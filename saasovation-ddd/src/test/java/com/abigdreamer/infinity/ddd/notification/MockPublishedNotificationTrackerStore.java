package com.abigdreamer.infinity.ddd.notification;

import java.util.List;

import com.abigdreamer.infinity.ddd.notification.Notification;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTracker;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTrackerStore;

public class MockPublishedNotificationTrackerStore
    implements PublishedNotificationTrackerStore {

    public MockPublishedNotificationTrackerStore() {
        super();
    }

    @Override
    public PublishedNotificationTracker publishedNotificationTracker() {
        return new PublishedNotificationTracker("mock");
    }

    @Override
    public PublishedNotificationTracker publishedNotificationTracker(String aTypeName) {
        return new PublishedNotificationTracker("mock");
    }

    @Override
    public void trackMostRecentPublishedNotification(
            PublishedNotificationTracker aPublishedNotificationTracker,
            List<Notification> aNotifications) {
        // no-op
    }

    @Override
    public String typeName() {
        return "mock";
    }
}
