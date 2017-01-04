package com.abigdreamer.infinity.ddd.event;

import java.util.List;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;



/**
 *  事件存储器
 * 
 * @author Darkness
 * @date 2014-5-5 下午7:58:31 
 * @version V1.0
 */
public interface EventStore {

	List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId, long aHighStoredEventId);

	List<StoredEvent> allStoredEventsSince(long aStoredEventId);

	StoredEvent append(DomainEvent aDomainEvent);

	void close();

	long countStoredEvents();
}
