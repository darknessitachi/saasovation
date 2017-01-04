package com.abigdreamer.infinity.ddd.event.sourcing;

public interface EventDispatcher {

	void dispatch(DispatchableDomainEvent aDispatchableDomainEvent);

	void registerEventDispatcher(EventDispatcher anEventDispatcher);

	boolean understands(DispatchableDomainEvent aDispatchableDomainEvent);
}
