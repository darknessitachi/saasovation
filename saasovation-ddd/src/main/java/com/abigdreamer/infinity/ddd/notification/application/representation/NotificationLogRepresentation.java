package com.abigdreamer.infinity.ddd.notification.application.representation;

import java.util.Collection;

import com.abigdreamer.infinity.ddd.media.Link;
import com.abigdreamer.infinity.ddd.notification.Notification;
import com.abigdreamer.infinity.ddd.notification.NotificationLog;


public class NotificationLogRepresentation {

    private boolean archived;
    private String id;
    private Collection<Notification> notifications;
    private Link linkNext;
    private Link linkPrevious;
    private Link linkSelf;

    public NotificationLogRepresentation(NotificationLog aLog) {
        this();

        this.initializeFrom(aLog);
    }

    public boolean getArchived() {
        return this.archived;
    }

    public String getId() {
        return this.id;
    }

    public void addNotification(Notification aNotification) {
        this.getNotifications().add(aNotification);
    }

    public Collection<Notification> getNotifications() {
        return this.notifications;
    }

    public int getNotificationsCount() {
        return this.getNotifications().size();
    }

    public boolean hasNotifications() {
        return this.getNotificationsCount() > 0;
    }

    public Link getLinkNext() {
        return this.linkNext;
    }

    public void setLinkNext(Link aNext) {
        this.linkNext = aNext;
    }

    public Link getLinkPrevious() {
        return this.linkPrevious;
    }

    public void setLinkPrevious(Link aPrevious) {
        this.linkPrevious = aPrevious;
    }

    public Link getLinkSelf() {
        return this.linkSelf;
    }

    public void setLinkSelf(Link aSelf) {
        this.linkSelf = aSelf;
    }

    protected NotificationLogRepresentation() {
        super();
    }

    private void initializeFrom(NotificationLog aLog) {
        this.setArchived(aLog.isArchived());
        this.setId(aLog.notificationLogId());
        this.setNotifications(aLog.notifications());
    }

    private void setArchived(boolean isArchived) {
        this.archived = isArchived;
    }

    private void setId(String anId) {
        this.id = anId;
    }

    private void setNotifications(
            Collection<Notification> aNotifications) {
        this.notifications = aNotifications;
    }
}
