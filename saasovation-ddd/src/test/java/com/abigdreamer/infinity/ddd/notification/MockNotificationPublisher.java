package com.abigdreamer.infinity.ddd.notification;

import com.abigdreamer.infinity.ddd.notification.NotificationPublisher;

/**
 * Mock通知发布器
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:33:55
 * @version V1.0
 */
public class MockNotificationPublisher implements NotificationPublisher {

    private boolean confirmed;

    public MockNotificationPublisher() {
        super();
    }

    @Override
    public void publishNotifications() {
        this.confirmed = true;
    }

    @Override
    public boolean internalOnlyTestConfirmation() {
        return this.confirmed;
    }
}
