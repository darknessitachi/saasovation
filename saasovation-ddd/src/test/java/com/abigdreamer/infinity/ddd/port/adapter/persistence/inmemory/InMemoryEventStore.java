package com.abigdreamer.infinity.ddd.port.adapter.persistence.inmemory;

import java.util.ArrayList;
import java.util.List;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.event.EventSerializer;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.StoredEvent;
import com.github.rapidark.framework.persistence.CleanableStore;


public class InMemoryEventStore implements EventStore, CleanableStore {

    private List<StoredEvent> storedEvents;

    public InMemoryEventStore() {
        super();

        this.storedEvents = new ArrayList<StoredEvent>();
    }

    @Override
    public List<StoredEvent> allStoredEventsBetween(
            long aLowStoredEventId,
            long aHighStoredEventId) {
        List<StoredEvent> events = new ArrayList<StoredEvent>();

        for (StoredEvent storedEvent : this.storedEvents) {
            if (storedEvent.eventId() >= aLowStoredEventId && storedEvent.eventId() <= aHighStoredEventId) {
                events.add(storedEvent);
            }
        }

        return events;
    }

    @Override
    public List<StoredEvent> allStoredEventsSince(long aStoredEventId) {
        List<StoredEvent> events = new ArrayList<StoredEvent>();

        for (StoredEvent storedEvent : this.storedEvents) {
            if (storedEvent.eventId() > aStoredEventId) {
                events.add(storedEvent);
            }
        }

        return events;
    }

    @Override
    public synchronized StoredEvent append(DomainEvent aDomainEvent) {
        String eventSerialization =
                EventSerializer.instance().serialize(aDomainEvent);

        StoredEvent storedEvent =
                new StoredEvent(
                        aDomainEvent.getClass().getName(),
                        aDomainEvent.occurredOn(),
                        eventSerialization,
                        this.storedEvents.size() + 1);

        this.storedEvents.add(storedEvent);

        return storedEvent;
    }

    @Override
    public void close() {
        this.clean();
    }

    @Override
    public long countStoredEvents() {
        return this.storedEvents.size();
    }

    @Override
    public void clean() {
        this.storedEvents = new ArrayList<StoredEvent>();
    }
}
