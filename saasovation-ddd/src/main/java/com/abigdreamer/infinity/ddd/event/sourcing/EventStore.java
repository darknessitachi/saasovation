package com.abigdreamer.infinity.ddd.event.sourcing;

import java.util.List;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;


public interface EventStore {

	void appendWith(EventStreamId aStartingIdentity, List<DomainEvent> anEvents);

	void close();

	List<DispatchableDomainEvent> eventsSince(long aLastReceivedEvent);

	EventStream eventStreamSince(EventStreamId anIdentity);

	EventStream fullEventStreamFor(EventStreamId anIdentity);

	void purge(); // mainly used for testing

	void registerEventNotifiable(EventNotifiable anEventNotifiable);
}
