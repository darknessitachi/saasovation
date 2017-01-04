package com.abigdreamer.infinity.ddd.notification.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.notification.NotificationLog;
import com.abigdreamer.infinity.ddd.notification.NotificationLogFactory;
import com.abigdreamer.infinity.ddd.notification.NotificationLogId;
import com.abigdreamer.infinity.ddd.notification.NotificationPublisher;


/**
 * 通知应用服务
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:19:19
 * @version V1.0
 */
public class NotificationApplicationService {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private NotificationPublisher notificationPublisher;

    public NotificationApplicationService() {
        super();
    }

    @Transactional(readOnly=true)
    public NotificationLog currentNotificationLog() {
        NotificationLogFactory factory = new NotificationLogFactory(this.eventStore());

        return factory.createCurrentNotificationLog();
    }

    @Transactional(readOnly=true)
    public NotificationLog notificationLog(String aNotificationLogId) {
        NotificationLogFactory factory = new NotificationLogFactory(this.eventStore());

        return factory.createNotificationLog(new NotificationLogId(aNotificationLogId));
    }

    @Transactional
    public void publishNotifications() {
        this.notificationPublisher().publishNotifications();
    }

    public EventStore eventStore() {
        return this.eventStore;
    }

    public NotificationPublisher notificationPublisher() {
        return this.notificationPublisher;
    }
}
