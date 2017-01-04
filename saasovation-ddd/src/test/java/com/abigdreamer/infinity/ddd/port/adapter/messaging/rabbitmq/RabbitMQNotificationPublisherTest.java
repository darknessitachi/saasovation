package com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.TestableDomainEvent;
import com.abigdreamer.infinity.ddd.notification.NotificationPublisher;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTrackerStore;
import com.abigdreamer.infinity.ddd.port.adapter.notification.RabbitMQNotificationPublisher;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.hibernate.HibernateEventStore;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.hibernate.HibernatePublishedNotificationTrackerStore;

import com.saasovation.common.CommonTestCase;

public class RabbitMQNotificationPublisherTest extends CommonTestCase {

	public RabbitMQNotificationPublisherTest() {
		super();
	}

	public void testPublishNotifications() throws Exception {
		EventStore eventStore = this.eventStore();

		assertNotNull(eventStore);

		PublishedNotificationTrackerStore publishedNotificationTrackerStore = new HibernatePublishedNotificationTrackerStore("unit.test");

		NotificationPublisher notificationPublisher = new RabbitMQNotificationPublisher(eventStore, publishedNotificationTrackerStore, "unit.test");

		assertNotNull(notificationPublisher);

		notificationPublisher.publishNotifications();
	}

	@Override
	protected void setUp() throws Exception {
		DomainEventPublisher.instance().reset();

		super.setUp();

		// always start with at least 20 events

		EventStore eventStore = this.eventStore();

		long startingDomainEventId = (new Date()).getTime();

		for (int idx = 0; idx < 20; ++idx) {
			long domainEventId = startingDomainEventId + 1;

			DomainEvent event = new TestableDomainEvent(domainEventId, "name" + domainEventId);

			eventStore.append(event);
		}
	}

	private EventStore eventStore() {
		EventStore eventStore = new HibernateEventStore();

		assertNotNull(eventStore);

		return eventStore;
	}
}
