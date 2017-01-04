package com.abigdreamer.infinity.ddd.domain.model;

/**
 * 事件监听
 * 
 * @author Darkness
 * @date 2014-12-23 下午4:53:35 
 * @version V1.0
 */
public interface DomainEventSubscriber<T> {

	void handleEvent(final T aDomainEvent);

	Class<T> subscribedToEventType();
}
