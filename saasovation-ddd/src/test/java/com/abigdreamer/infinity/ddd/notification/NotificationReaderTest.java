package com.abigdreamer.infinity.ddd.notification;

import static org.junit.Assert.*;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.event.TestableDomainEvent;
import com.abigdreamer.infinity.ddd.event.TestableNavigableDomainEvent;
import com.abigdreamer.infinity.ddd.notification.Notification;
import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import com.abigdreamer.infinity.ddd.notification.NotificationSerializer;
import org.junit.Test;

/**
 * 通知读取器测试
 * 
 * @author Darkness
 * @date 2014-12-23 下午1:41:53 
 * @version V1.0
 */
public class NotificationReaderTest {

    // 读取通知基本信息
	@Test
    public void testReadBasicProperties() throws Exception {
        DomainEvent domainEvent = new TestableDomainEvent(100, "testing");

        Notification notification = new Notification(1, domainEvent);

        NotificationSerializer serializer = NotificationSerializer.instance();

        String serializedNotification = serializer.serialize(notification);

        NotificationReader reader = new NotificationReader(serializedNotification);

        assertEquals(1L, reader.notificationId());
        assertEquals("1", reader.notificationIdAsString());
        assertEquals(domainEvent.occurredOn(), reader.occurredOn());
        assertEquals(notification.typeName(), reader.typeName());
        assertEquals(notification.version(), reader.version());
        assertEquals(domainEvent.eventVersion(), reader.version());
    }

    // 读取事件属性
	@Test
    public void testReadDomainEventProperties() throws Exception {
        TestableDomainEvent domainEvent = new TestableDomainEvent(100, "testing");

        Notification notification = new Notification(1, domainEvent);

        NotificationSerializer serializer = NotificationSerializer.instance();

        String serializedNotification = serializer.serialize(notification);

        NotificationReader reader = new NotificationReader(serializedNotification);

        assertEquals("" + domainEvent.eventVersion(), reader.eventStringValue("eventVersion"));
        assertEquals("" + domainEvent.eventVersion(), reader.eventStringValue("/eventVersion"));
        assertEquals("" + domainEvent.id(), reader.eventStringValue("id"));
        assertEquals("" + domainEvent.id(), reader.eventStringValue("/id"));
        assertEquals("" + domainEvent.name(), reader.eventStringValue("name"));
        assertEquals("" + domainEvent.name(), reader.eventStringValue("/name"));
        assertEquals("" + domainEvent.occurredOn().getTime(), reader.eventStringValue("occurredOn"));
        assertEquals("" + domainEvent.occurredOn().getTime(), reader.eventStringValue("/occurredOn"));
    }

    // 读取嵌套事件属性
	@Test
    public void testReadNestedDomainEventProperties() throws Exception {
        TestableNavigableDomainEvent domainEvent = new TestableNavigableDomainEvent(100, "testing");

        Notification notification = new Notification(1, domainEvent);

        NotificationSerializer serializer = NotificationSerializer.instance();

        String serializedNotification = serializer.serialize(notification);

        NotificationReader reader = new NotificationReader(serializedNotification);

        assertEquals("" + domainEvent.eventVersion(), reader.eventStringValue("eventVersion"));
        assertEquals("" + domainEvent.eventVersion(), reader.eventStringValue("/eventVersion"));
        assertEquals(domainEvent.eventVersion(), reader.eventIntegerValue("eventVersion").intValue());
        assertEquals(domainEvent.eventVersion(), reader.eventIntegerValue("/eventVersion").intValue());
        assertEquals("" + domainEvent.nestedEvent().eventVersion(), reader.eventStringValue("nestedEvent", "eventVersion"));
        assertEquals("" + domainEvent.nestedEvent().eventVersion(), reader.eventStringValue("/nestedEvent/eventVersion"));
        assertEquals(domainEvent.nestedEvent().eventVersion(), reader.eventIntegerValue("nestedEvent", "eventVersion").intValue());
        assertEquals(domainEvent.nestedEvent().eventVersion(), reader.eventIntegerValue("/nestedEvent/eventVersion").intValue());
        assertEquals("" + domainEvent.nestedEvent().id(), reader.eventStringValue("nestedEvent", "id"));
        assertEquals("" + domainEvent.nestedEvent().id(), reader.eventStringValue("/nestedEvent/id"));
        assertEquals(domainEvent.nestedEvent().id(), reader.eventLongValue("nestedEvent", "id").longValue());
        assertEquals(domainEvent.nestedEvent().id(), reader.eventLongValue("/nestedEvent/id").longValue());
        assertEquals("" + domainEvent.nestedEvent().name(), reader.eventStringValue("nestedEvent", "name"));
        assertEquals("" + domainEvent.nestedEvent().name(), reader.eventStringValue("/nestedEvent/name"));
        assertEquals("" + domainEvent.nestedEvent().occurredOn().getTime(), reader.eventStringValue("nestedEvent", "occurredOn"));
        assertEquals("" + domainEvent.nestedEvent().occurredOn().getTime(), reader.eventStringValue("/nestedEvent/occurredOn"));
        assertEquals(domainEvent.nestedEvent().occurredOn(), reader.eventDateValue("nestedEvent", "occurredOn"));
        assertEquals(domainEvent.nestedEvent().occurredOn(), reader.eventDateValue("/nestedEvent/occurredOn"));
        assertEquals("" + domainEvent.occurredOn().getTime(), reader.eventStringValue("occurredOn"));
        assertEquals("" + domainEvent.occurredOn().getTime(), reader.eventStringValue("/occurredOn"));
        assertEquals(domainEvent.occurredOn(), reader.eventDateValue("occurredOn"));
        assertEquals(domainEvent.occurredOn(), reader.eventDateValue("/occurredOn"));
    }

    // 点标记读取属性
	@Test
    public void testDotNotation() throws Exception {
        TestableNavigableDomainEvent domainEvent = new TestableNavigableDomainEvent(100, "testing");

        Notification notification = new Notification(1, domainEvent);

        NotificationSerializer serializer = NotificationSerializer.instance();

        String serializedNotification = serializer.serialize(notification);

        NotificationReader reader = new NotificationReader(serializedNotification);

        assertEquals("" + domainEvent.nestedEvent().eventVersion(), reader.eventStringValue("nestedEvent.eventVersion"));
        assertEquals(domainEvent.nestedEvent().eventVersion(), reader.eventIntegerValue("nestedEvent.eventVersion").intValue());
    }

    // 读取假冒的属性，抛出异常
	@Test
    public void testReadBogusProperties() throws Exception {
        TestableNavigableDomainEvent domainEvent = new TestableNavigableDomainEvent(100L, "testing");

        Notification notification = new Notification(1, domainEvent);

        NotificationSerializer serializer = NotificationSerializer.instance();

        String serializedNotification = serializer.serialize(notification);

        NotificationReader reader = new NotificationReader(serializedNotification);

        boolean mustThrow = false;

        try {
            reader.eventStringValue("eventVersion.version");
        } catch (Exception e) {
            mustThrow = true;
        }

        assertTrue(mustThrow);
    }

    // 读取null属性
	@Test
    public void testReadNullProperties() throws Exception {
        TestableNullPropertyDomainEvent domainEvent = new TestableNullPropertyDomainEvent(100L, "testingNulls");

        Notification notification = new Notification(1, domainEvent);

        NotificationSerializer serializer = NotificationSerializer.instance();

        String serializedNotification = serializer.serialize(notification);

        NotificationReader reader = new NotificationReader(serializedNotification);

        assertNull(reader.eventStringValue("textMustBeNull"));

        assertNull(reader.eventStringValue("textMustBeNull2"));

        assertNull(reader.eventIntegerValue("numberMustBeNull"));

        assertNull(reader.eventStringValue("nested.nestedTextMustBeNull"));

        assertNull(reader.eventStringValue("nullNested.nestedTextMustBeNull"));

        assertNull(reader.eventStringValue("nested.nestedDeeply.nestedDeeplyTextMustBeNull"));

        assertNull(reader.eventStringValue("nested.nestedDeeply.nestedDeeplyTextMustBeNull2"));

        assertNull(reader.eventStringValue("nested.nullNestedDeeply.nestedDeeplyTextMustBeNull"));

        assertNull(reader.eventStringValue("nested.nullNestedDeeply.nestedDeeplyTextMustBeNull2"));
    }
}
