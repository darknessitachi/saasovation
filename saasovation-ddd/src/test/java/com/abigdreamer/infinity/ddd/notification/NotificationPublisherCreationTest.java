package com.abigdreamer.infinity.ddd.notification;

import static org.junit.Assert.*;

import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.MockEventStore;
import com.abigdreamer.infinity.ddd.notification.NotificationPublisher;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTrackerStore;
import com.abigdreamer.infinity.ddd.port.adapter.notification.RabbitMQNotificationPublisher;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.hibernate.HibernatePublishedNotificationTrackerStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:common/applicationContext-*.xml"}) 
public class NotificationPublisherCreationTest  {

	@Test
    public void newNotificationPublisher() throws Exception {

        EventStore eventStore = new MockEventStore();

        assertNotNull(eventStore);

        PublishedNotificationTrackerStore publishedNotificationTrackerStore =
                new HibernatePublishedNotificationTrackerStore("unit.test");

        NotificationPublisher notificationPublisher =
                new RabbitMQNotificationPublisher(
                        eventStore,
                        publishedNotificationTrackerStore,
                        "unit.test");

        assertNotNull(notificationPublisher);
    }
}
