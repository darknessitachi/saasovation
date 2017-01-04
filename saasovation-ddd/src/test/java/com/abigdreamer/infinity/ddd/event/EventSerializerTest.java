package com.abigdreamer.infinity.ddd.event;

import com.abigdreamer.infinity.ddd.event.EventSerializer;
import com.abigdreamer.infinity.ddd.event.TestableDomainEvent;


import junit.framework.TestCase;

public class EventSerializerTest extends TestCase {

    public EventSerializerTest() {
        super();
    }

    public void testDefaultFormat() throws Exception {
        EventSerializer serializer = EventSerializer.instance();

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        assertTrue(serializedEvent.contains("\"id\""));
        assertTrue(serializedEvent.contains("\"occurredOn\""));
        assertFalse(serializedEvent.contains("\n"));
        assertTrue(serializedEvent.contains("null"));
    }

    public void testCompact() throws Exception {
        EventSerializer serializer = new EventSerializer(true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        assertTrue(serializedEvent.contains("\"id\""));
        assertTrue(serializedEvent.contains("\"occurredOn\""));
        assertFalse(serializedEvent.contains("\n"));
        assertFalse(serializedEvent.contains("null"));
    }

    public void testPrettyAndCompact() throws Exception {
        EventSerializer serializer = new EventSerializer(true, true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        assertTrue(serializedEvent.contains("\"id\""));
        assertTrue(serializedEvent.contains("\"occurredOn\""));
        assertTrue(serializedEvent.contains("\n"));
        assertFalse(serializedEvent.contains("null"));
    }

    public void testDeserializeDefault() throws Exception {
        EventSerializer serializer = EventSerializer.instance();

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertTrue(serializedEvent.contains("null"));
        assertEquals(1, event.id());
        assertEquals(null, event.name());
        assertNotNull(event.occurredOn());
    }

    public void testDeserializeCompactNotNull() throws Exception {
        EventSerializer serializer = new EventSerializer(true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, "test"));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertFalse(serializedEvent.contains("null"));
        assertTrue(serializedEvent.contains("\"test\""));
        assertEquals(1, event.id());
        assertEquals("test", event.name());
        assertNotNull(event.occurredOn());
    }

    public void testDeserializeCompactNull() throws Exception {
        EventSerializer serializer = new EventSerializer(true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertFalse(serializedEvent.contains("null"));
        assertEquals(1, event.id());
        assertEquals(null, event.name());
        assertNotNull(event.occurredOn());
    }

    public void testDeserializePrettyAndCompactNull() throws Exception {
        EventSerializer serializer = new EventSerializer(true, true);

        String serializedEvent = serializer.serialize(new TestableDomainEvent(1, null));

        TestableDomainEvent event = serializer.deserialize(serializedEvent, TestableDomainEvent.class);

        assertFalse(serializedEvent.contains("null"));
        assertTrue(serializedEvent.contains("\n"));
        assertEquals(1, event.id());
        assertEquals(null, event.name());
        assertNotNull(event.occurredOn());
    }
}
