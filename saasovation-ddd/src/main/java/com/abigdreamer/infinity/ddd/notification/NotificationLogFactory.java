package com.abigdreamer.infinity.ddd.notification;

import java.util.ArrayList;
import java.util.List;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.StoredEvent;


/**
 *  通知日志工厂
 * 
 * @author Darkness
 * @date 2014-5-5 下午8:26:58 
 * @version V1.0
 */
public class NotificationLogFactory {

    // this could be a configuration
	// 日志包含的最大通知数
    private static final int NOTIFICATIONS_PER_LOG = 20;

    private EventStore eventStore;

    public static int notificationsPerLog() {
        return NOTIFICATIONS_PER_LOG;
    }

    public NotificationLogFactory(EventStore anEventStore) {
        super();

        this.setEventStore(anEventStore);
    }

    /**
     * 当前日志
     *  
     * @author Darkness
     * @date 2014-5-22 下午8:40:47
     * @version V1.0
     * @since ark 1.0
     */
    public NotificationLog createCurrentNotificationLog() {
        return this.createNotificationLog(
                this.calculateCurrentNotificationLogId(eventStore));
    }

    /**
     * 创建存档日志
     *  
     * @author Darkness
     * @date 2014-5-22 下午8:41:00
     * @version V1.0
     * @since ark 1.0
     */
    public NotificationLog createNotificationLog(NotificationLogId aNotificationLogId) {

        long count = this.eventStore().countStoredEvents();

        NotificationLogInfo info = new NotificationLogInfo(aNotificationLogId, count);

        return this.createNotificationLog(info);
    }

    /**
     *  计算通知日志标识，由于当前日志可能一直处于改变状态，在每次请求时都需要重新计算日志标识
     * 
     * @author Darkness
     * @date 2014-5-5 下午8:27:54 
     * @version V1.0
     */
    private NotificationLogInfo calculateCurrentNotificationLogId(EventStore anEventStore) {

        long count = anEventStore.countStoredEvents();

        long remainder = count % NOTIFICATIONS_PER_LOG;

        if (remainder == 0) {
            remainder = NOTIFICATIONS_PER_LOG;
        }

        long low = count - remainder + 1;

        // 虽然当前有可能不存在一整套的通知，但是日志的应该是崭新的
        // ensures a minted id value even though there may
        // not be a full set of notifications at present
        long high = low + NOTIFICATIONS_PER_LOG - 1;

        return new NotificationLogInfo(new NotificationLogId(low, high), count);
    }

    /**
     * 创建通知日志
     *  
     * @author Darkness
     * @date 2014-5-22 下午8:57:27
     * @version V1.0
     * @since ark 1.0
     */
    private NotificationLog createNotificationLog(NotificationLogInfo aNotificationLogInfo) {

        List<StoredEvent> storedEvents =
            this.eventStore().allStoredEventsBetween(
                    aNotificationLogInfo.notificationLogId().low(),
                    aNotificationLogInfo.notificationLogId().high());

        // 存档指示器
        boolean archivedIndicator =
                aNotificationLogInfo.notificationLogId().high() < aNotificationLogInfo.totalLogged();

        NotificationLogId next = archivedIndicator ?
                aNotificationLogInfo.notificationLogId().next(NOTIFICATIONS_PER_LOG) :
                null;

        NotificationLogId previous =
                aNotificationLogInfo.notificationLogId().previous(NOTIFICATIONS_PER_LOG);

        NotificationLog notificationLog =
            new NotificationLog(
                    aNotificationLogInfo.notificationLogId().encoded(),
                    NotificationLogId.encoded(next),
                    NotificationLogId.encoded(previous),
                    this.notificationsFrom(storedEvents),
                    archivedIndicator);

        return notificationLog;
    }

    /**
     * 将存储的事件包装成通知
     *  
     * @author Darkness
     * @date 2014-5-22 下午9:07:39
     * @version V1.0
     * @since ark 1.0
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

    private EventStore eventStore() {
        return eventStore;
    }

    private void setEventStore(EventStore anEventStore) {
        this.eventStore = anEventStore;
    }
}
