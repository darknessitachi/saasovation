package com.abigdreamer.infinity.ddd.notification;

/**
 * 通知发布器
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:34:38
 * @version V1.0
 */
public interface NotificationPublisher {

	void publishNotifications();

	boolean internalOnlyTestConfirmation();
}
