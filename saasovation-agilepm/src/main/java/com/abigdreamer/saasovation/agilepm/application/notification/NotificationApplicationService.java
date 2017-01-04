package com.abigdreamer.saasovation.agilepm.application.notification;

import com.abigdreamer.infinity.ddd.notification.NotificationPublisher;
import com.abigdreamer.saasovation.agilepm.application.ApplicationServiceLifeCycle;


/**
 * 通知应用服务
 * 
 * @author Darkness
 * @date 2014-5-26 下午4:18:32
 * @version V1.0
 */
public class NotificationApplicationService {

    private NotificationPublisher notificationPublisher;

    public NotificationApplicationService(NotificationPublisher aNotificationPublisher) {
        super();

        this.notificationPublisher = aNotificationPublisher;
    }

    public void publishNotifications() {
        ApplicationServiceLifeCycle.begin(false);

        try {
            this.notificationPublisher().publishNotifications();

            ApplicationServiceLifeCycle.success();

        } catch (RuntimeException e) {
            ApplicationServiceLifeCycle.fail(e);
        }
    }

    private NotificationPublisher notificationPublisher() {
        return this.notificationPublisher;
    }
}
