package com.abigdreamer.infinity.ddd.event;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;


/**
 *  存储所有的领域事件
 * 
 * @author Darkness
 * @date 2014-5-5 下午5:19:56 
 * @version V1.0
 */
@Aspect
public class DomainEventStoreProcessor {

    @Autowired
    private EventStore eventStore;

    /**
     * Registers a DomainEventStoreProcessor to listen
     * and forward all domain events to external subscribers.
     * This factory method is provided in the case where
     * Spring AOP wiring is not desired.
     */
    public static void register() {
        (new DomainEventStoreProcessor()).listen();
    }

    /**
     * Constructs my default state.
     */
    public DomainEventStoreProcessor() {
        super();
    }

    /**
     * Listens for all domain events and stores them.
     */
    @Before("execution(* org.infinite..application..*Service.*(..))")
    public void listen() {
        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<DomainEvent>() {

                public void handleEvent(DomainEvent aDomainEvent) {
                    store(aDomainEvent);
                }

                public Class<DomainEvent> subscribedToEventType() {
                    return DomainEvent.class; // all domain events
                }
            });
    }

    /**
     * Stores aDomainEvent to the event store.
     * @param aDomainEvent the DomainEvent to store
     */
    private void store(DomainEvent aDomainEvent) {
        this.eventStore().append(aDomainEvent);
    }

    /**
     * Answers my EventStore.
     * @return EventStore
     */
    private EventStore eventStore() {
        return this.eventStore;
    }
}
