package com.abigdreamer.infinity.ddd.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  通知日志
 * 
 * @author Darkness
 * @date 2014-5-5 下午8:20:52 
 * @version V1.0
 */
public class NotificationLog {

    private boolean archived;// 是否存档
    private List<Notification> notifications;
    
    private String notificationLogId;
    private String nextNotificationLogId;
    private String previousNotificationLogId;

    public NotificationLog(
            String aNotificationLogId,
            String aNextNotificationLogId,
            String aPreviousNotificationLogId,
            List<Notification> aNotifications,
            boolean anArchivedIndicator) {

        super();

        this.setArchived(anArchivedIndicator);
        this.setNotifications(aNotifications);
        
        this.setNextNotificationLogId(aNextNotificationLogId);
        this.setNotificationLogId(aNotificationLogId);
        this.setPreviousNotificationLogId(aPreviousNotificationLogId);
    }

    public boolean isArchived() {
        return this.archived;
    }

    public List<Notification> notifications() {
        return Collections.unmodifiableList(this.notifications);
    }

    public NotificationLogId decodedNotificationLogId() {
        return new NotificationLogId(this.notificationLogId());
    }

    public String notificationLogId() {
        return this.notificationLogId;
    }

    public NotificationLogId decodedNextNotificationLogId() {
        return new NotificationLogId(this.nextNotificationLogId());
    }

    public String nextNotificationLogId() {
        return this.nextNotificationLogId;
    }

    public boolean hasNextNotificationLog() {
        return this.nextNotificationLogId() != null;
    }

    public NotificationLogId decodedPreviousNotificationLogId() {
        return new NotificationLogId(this.previousNotificationLogId());
    }

    public String previousNotificationLogId() {
        return this.previousNotificationLogId;
    }

    public boolean hasPreviousNotificationLog() {
        return this.previousNotificationLogId() != null;
    }

    public int totalNotifications() {
        return this.notifications.size();
    }

    protected NotificationLog() {
        super();

        this.setNotifications(new ArrayList<Notification>());
    }

    private void setNotifications(List<Notification> aNotifications) {
        this.notifications = aNotifications;
    }

    private void setNotificationLogId(String aNotificationLogId) {
        this.notificationLogId = aNotificationLogId;
    }

    private void setNextNotificationLogId(String aNextNotificationLogId) {
        this.nextNotificationLogId = aNextNotificationLogId;
    }

    private void setPreviousNotificationLogId(String aPreviousNotificationLogId) {
        this.previousNotificationLogId = aPreviousNotificationLogId;
    }

    private void setArchived(boolean aArchived) {
        this.archived = aArchived;
    }
}
