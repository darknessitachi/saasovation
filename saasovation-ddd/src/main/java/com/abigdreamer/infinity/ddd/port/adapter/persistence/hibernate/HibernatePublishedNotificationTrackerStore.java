package com.abigdreamer.infinity.ddd.port.adapter.persistence.hibernate;

import java.util.List;

import org.hibernate.Query;

import com.abigdreamer.infinity.ddd.notification.Notification;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTracker;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTrackerStore;
import com.rapidark.framework.persistence.hibernate.HibernateSessionSupport;



public class HibernatePublishedNotificationTrackerStore
    extends HibernateSessionSupport
    implements PublishedNotificationTrackerStore {

    private String typeName;

    public HibernatePublishedNotificationTrackerStore(
            String aPublishedNotificationTrackerType) {
        this();

        this.setTypeName(aPublishedNotificationTrackerType);
    }

    public HibernatePublishedNotificationTrackerStore() {
        super();
    }

    @Override
    public PublishedNotificationTracker publishedNotificationTracker() {
        return this.publishedNotificationTracker(this.typeName());
    }

    @Override
    public PublishedNotificationTracker publishedNotificationTracker(String aTypeName) {
        Query query =
                this.session().createQuery(
                        "from PublishedNotificationTracker as pnt "
                        + "where pnt.typeName = ?");

        query.setParameter(0, aTypeName);

        PublishedNotificationTracker publishedNotificationTracker = null;

        try {
            publishedNotificationTracker =
                    (PublishedNotificationTracker) query.uniqueResult();
        } catch (Exception e) {
            // fall through
        }

        if (publishedNotificationTracker == null) {
            publishedNotificationTracker =
                    new PublishedNotificationTracker(this.typeName());
        }

        return publishedNotificationTracker;
    }

    @Override
    public void trackMostRecentPublishedNotification(
            PublishedNotificationTracker aPublishedNotificationTracker,
            List<Notification> aNotifications) {
        int lastIndex = aNotifications.size() - 1;

        if (lastIndex >= 0) {
            long mostRecentId = aNotifications.get(lastIndex).notificationId();

            aPublishedNotificationTracker.setMostRecentPublishedNotificationId(mostRecentId);

            this.session().save(aPublishedNotificationTracker);
        }
    }

    @Override
    public String typeName() {
        return typeName;
    }

    private void setTypeName(String aTypeName) {
        this.typeName = aTypeName;
    }
}
