package com.abigdreamer.infinity.ddd.domain.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abigdreamer.infinity.common.lang.AssertionConcern;


/**
 * 事件源根实体
 * 
 * @author Darkness
 * @date 2014-5-31 下午1:48:04
 * @version V1.0
 */
public abstract class EventSourcedRootEntity extends AssertionConcern {

    private static final String MUTATOR_METHOD_NAME = "when";

    private static Map<String, Method> mutatorMethods =
            new HashMap<String, Method>();

    private List<DomainEvent> mutatingEvents;
    private int unmutatedVersion;

    public int mutatedVersion() {
        return this.unmutatedVersion() + 1;
    }

    public List<DomainEvent> mutatingEvents() {
        return this.mutatingEvents;
    }

    public int unmutatedVersion() {
        return this.unmutatedVersion;
    }

    protected EventSourcedRootEntity(
            List<DomainEvent> anEventStream,
            int aStreamVersion) {

        this();

        for (DomainEvent event : anEventStream) {
            this.mutateWhen(event);
        }

        this.setUnmutatedVersion(aStreamVersion);
    }

    protected EventSourcedRootEntity() {
        super();

        this.setMutatingEvents(new ArrayList<DomainEvent>(2));
    }

    protected void apply(DomainEvent aDomainEvent) {

        this.mutatingEvents().add(aDomainEvent);

        this.mutateWhen(aDomainEvent);
    }

    protected void mutateWhen(DomainEvent aDomainEvent) {

        Class<? extends EventSourcedRootEntity> rootType = this.getClass();

        Class<? extends DomainEvent> eventType = aDomainEvent.getClass();

        String key = rootType.getName() + ":" + eventType.getName();

        Method mutatorMethod = mutatorMethods.get(key);

        if (mutatorMethod == null) {
            mutatorMethod = this.cacheMutatorMethodFor(key, rootType, eventType);
        }

        try {
            mutatorMethod.invoke(this, aDomainEvent);

        } catch (InvocationTargetException e) {
            if (e.getCause() != null) {
                throw new RuntimeException(
                        "Method "
                                + MUTATOR_METHOD_NAME
                                + "("
                                + eventType.getSimpleName()
                                + ") failed. See cause: "
                                + e.getMessage(),
                        e.getCause());
            }

            throw new RuntimeException(
                    "Method "
                            + MUTATOR_METHOD_NAME
                            + "("
                            + eventType.getSimpleName()
                            + ") failed. See cause: "
                            + e.getMessage(),
                    e);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(
                    "Method "
                            + MUTATOR_METHOD_NAME
                            + "("
                            + eventType.getSimpleName()
                            + ") failed because of illegal access. See cause: "
                            + e.getMessage(),
                    e);
        }
    }

    private Method cacheMutatorMethodFor(
            String aKey,
            Class<? extends EventSourcedRootEntity> aRootType,
            Class<? extends DomainEvent> anEventType) {

        synchronized (mutatorMethods) {
            try {
                Method method = this.hiddenOrPublicMethod(aRootType, anEventType);

                method.setAccessible(true);

                mutatorMethods.put(aKey, method);

                return method;

            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "I do not understand "
                                + MUTATOR_METHOD_NAME
                                + "("
                                + anEventType.getSimpleName()
                                + ") because: "
                                + e.getClass().getSimpleName() + ">>>" + e.getMessage(),
                        e);
            }
        }
    }

    private Method hiddenOrPublicMethod(
            Class<? extends EventSourcedRootEntity> aRootType,
            Class<? extends DomainEvent> anEventType)
    throws Exception {

        Method method = null;

        try {

            // assume protected or private...

            method = aRootType.getDeclaredMethod(
                    MUTATOR_METHOD_NAME,
                    anEventType);

        } catch (Exception e) {

            // then public...

            method = aRootType.getMethod(
                    MUTATOR_METHOD_NAME,
                    anEventType);
        }

        return method;
    }

    private void setMutatingEvents(List<DomainEvent> aMutatingEventsList) {
        this.mutatingEvents = aMutatingEventsList;
    }

    private void setUnmutatedVersion(int aStreamVersion) {
        this.unmutatedVersion = aStreamVersion;
    }
}
