package com.abigdreamer.infinity.ddd.event;

import com.abigdreamer.infinity.common.serializer.AbstractSerializer;
import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;


/**
 *  事件序列化处理器
 * 
 * @author Darkness
 * @date 2014-5-5 下午8:08:48 
 * @version V1.0
 */
public class EventSerializer extends AbstractSerializer {

    private static EventSerializer eventSerializer;

    public static synchronized EventSerializer instance() {
        if (EventSerializer.eventSerializer == null) {
            EventSerializer.eventSerializer = new EventSerializer();
        }

        return EventSerializer.eventSerializer;
    }
 
    private EventSerializer() {
        this(false, false);
    }
 
    public EventSerializer(boolean isCompact) {
        this(false, isCompact);
    }

    public EventSerializer(boolean isPretty, boolean isCompact) {
        super(isPretty, isCompact);
    }

    /**
     *  序列化事件
     * 
     * @author Darkness
     * @date 2014-5-5 下午8:12:33 
     * @version V1.0
     */
    public String serialize(DomainEvent aDomainEvent) {
        String serialization = this.gson().toJson(aDomainEvent);

        return serialization;
    }

    /**
     *  反序列化事件
     * 
     * @author Darkness
     * @date 2014-5-5 下午8:13:23 
     * @version V1.0
     */
    public <T extends DomainEvent> T deserialize(String aSerialization, final Class<T> aType) {
        T domainEvent = this.gson().fromJson(aSerialization, aType);

        return domainEvent;
    }

}
