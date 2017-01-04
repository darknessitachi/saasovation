package com.abigdreamer.infinity.ddd.port.adapter.notification;

import java.util.ArrayList;
import java.util.List;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.StoredEvent;
import com.abigdreamer.infinity.ddd.notification.Notification;
import com.abigdreamer.infinity.ddd.notification.NotificationPublisher;
import com.abigdreamer.infinity.ddd.notification.NotificationSerializer;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTracker;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTrackerStore;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.ConnectionSettings;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.Exchange;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.MessageParameters;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.MessageProducer;


/**
 *  RabbitMQ通知发布器
 * 
 * @author Darkness
 * @date 2014-5-5 下午7:26:03 
 * @version V1.0
 */
public class RabbitMQNotificationPublisher implements NotificationPublisher {

    private EventStore eventStore;
    private String exchangeName;

    private PublishedNotificationTrackerStore publishedNotificationTrackerStore;

    public RabbitMQNotificationPublisher(
            EventStore anEventStore,
            PublishedNotificationTrackerStore aPublishedNotificationTrackerStore,
            Object aMessagingLocator) {

        super();

        this.setEventStore(anEventStore);
        this.setExchangeName((String) aMessagingLocator);
        this.setPublishedNotificationTrackerStore(aPublishedNotificationTrackerStore);
    }

    @Override
    public void publishNotifications() {
        PublishedNotificationTracker publishedNotificationTracker =
                this.publishedNotificationTrackerStore().publishedNotificationTracker();

        List<Notification> notifications =
            this.listUnpublishedNotifications(
                    publishedNotificationTracker.mostRecentPublishedNotificationId());

        MessageProducer messageProducer = this.messageProducer();

        try {
            for (Notification notification : notifications) {
                this.publish(notification, messageProducer);
            }

            this.publishedNotificationTrackerStore()
                .trackMostRecentPublishedNotification(
                    publishedNotificationTracker,
                    notifications);
        } finally {
            messageProducer.close();
        }
    }

    @Override
    public boolean internalOnlyTestConfirmation() {
        throw new UnsupportedOperationException("Not supported by production implementation.");
    }

    private EventStore eventStore() {
        return this.eventStore;
    }

    private void setEventStore(EventStore anEventStore) {
        this.eventStore = anEventStore;
    }

    private String exchangeName() {
        return this.exchangeName;
    }

    private void setExchangeName(String anExchangeName) {
        this.exchangeName = anExchangeName;
    }

    /**
     *  列出未发布的通知，该方法返回那些eventId大于aMostRecentPublishedMessageId的StoreEvent
     * 
     * @param aMostRecentPublishedMessageId 最近一次发布的消息id
     * @author Darkness
     * @date 2014-5-5 下午8:42:28 
     * @version V1.0
     */
	private List<Notification> listUnpublishedNotifications(long aMostRecentPublishedMessageId) {
		List<StoredEvent> storedEvents = this.eventStore().allStoredEventsSince(aMostRecentPublishedMessageId);

		List<Notification> notifications = this.notificationsFrom(storedEvents);

		return notifications;
	}

	/**
	 *  消息发布器，确保扇出交换器是存在的，并且获取一个用于发布消息的MessageProducer实例
	 * 
	 * @author Darkness
	 * @date 2014-5-5 下午8:51:22 
	 * @version V1.0
	 */
    private MessageProducer messageProducer() {

        // creates my exchange if non-existing
    	// 当交换器不存在时，创建一个交换器
        Exchange exchange =
            Exchange.fanOutInstance(
                    ConnectionSettings.instance(),
                    this.exchangeName(),
                    true);

        // create a message producer used to forward events
        // 创建一个消息发布器以转发事件
        MessageProducer messageProducer = MessageProducer.instance(exchange);

        return messageProducer;
    }

    /**
     *  根据存储的事件创建通知集合
     * 
     * @author Darkness
     * @date 2014-5-5 下午8:44:44 
     * @version V1.0
     */
	private List<Notification> notificationsFrom(List<StoredEvent> aStoredEvents) {
		List<Notification> notifications = new ArrayList<Notification>(aStoredEvents.size());

		for (StoredEvent storedEvent : aStoredEvents) {
			DomainEvent domainEvent = storedEvent.toDomainEvent();

			Notification notification = new Notification(storedEvent.eventId(), domainEvent);

			notifications.add(notification);
		}

		return notifications;
	}

	/**
	 *  发布单个通知
	 * 
	 * @author Darkness
	 * @date 2014-5-5 下午8:47:50 
	 * @version V1.0
	 */
    private void publish(Notification aNotification, MessageProducer aMessageProducer) {

        MessageParameters messageParameters =
            MessageParameters.durableTextParameters(
                    aNotification.typeName(),
                    Long.toString(aNotification.notificationId()),
                    aNotification.occurredOn());

		String notification = NotificationSerializer.instance().serialize(aNotification);

        aMessageProducer.send(notification, messageParameters);
    }

    private PublishedNotificationTrackerStore publishedNotificationTrackerStore() {
        return publishedNotificationTrackerStore;
    }

    private void setPublishedNotificationTrackerStore(PublishedNotificationTrackerStore publishedNotificationTrackerStore) {
        this.publishedNotificationTrackerStore = publishedNotificationTrackerStore;
    }
}
