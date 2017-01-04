package com.abigdreamer.infinity.ddd.port.adapter.persistence.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.event.EventSerializer;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.StoredEvent;
import com.abigdreamer.infinity.persistence.hibernate.HibernateSessionSupport;
import com.github.rapidark.framework.persistence.CleanableStore;


/**
 * Hibernate事件存储器实现
 *  
 * @author Darkness
 * @date 2014-5-22 下午9:08:44
 * @version V1.0
 * @since ark 1.0
 */
public class HibernateEventStore
    extends HibernateSessionSupport
    implements EventStore, CleanableStore {

	public HibernateEventStore() {
        super();
    }

    /**
     * 查询存储的事件
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId, long aHighStoredEventId) {
        Query query = this.session().createQuery(
                        "from StoredEvent as _obj_ "
                        + "where _obj_.eventId between ? and ? "
                        + "order by _obj_.eventId");

        query.setParameter(0, aLowStoredEventId);
        query.setParameter(1, aHighStoredEventId);

        List<StoredEvent> storedEvents = query.list();

        return storedEvents;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StoredEvent> allStoredEventsSince(long aStoredEventId) {
        Query query =
                this.session().createQuery(
                        "from StoredEvent as _obj_ "
                        + "where _obj_.eventId > ? "
                        + "order by _obj_.eventId");

        query.setParameter(0, aStoredEventId);

        List<StoredEvent> storedEvents = query.list();

        return storedEvents;
    }

    @Override
    public StoredEvent append(DomainEvent aDomainEvent) {
        String eventSerialization = EventSerializer.instance().serialize(aDomainEvent);

        StoredEvent storedEvent =
                new StoredEvent(
                        aDomainEvent.getClass().getName(),
                        aDomainEvent.occurredOn(),
                        eventSerialization);

        this.session().save(storedEvent);

        return storedEvent;
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public long countStoredEvents() {
        Query query =
                this.session().createQuery("select count(*) from StoredEvent");

        long count = ((Long) query.uniqueResult()).longValue();

        return count;
    }

	@Override
	public void clean() {
		
	}
}
