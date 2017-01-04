package com.abigdreamer.infinity.ddd.notification;

import java.util.List;

/**
 * 发布通知追踪存储器
 * 
 * @author Darkness
 * @date 2014-5-31 下午7:35:59
 * @version V1.0
 */
public interface PublishedNotificationTrackerStore {

	PublishedNotificationTracker publishedNotificationTracker();

	PublishedNotificationTracker publishedNotificationTracker(String aTypeName);

	void trackMostRecentPublishedNotification(PublishedNotificationTracker aPublishedNotificationTracker, List<Notification> aNotifications);

	String typeName();
}
