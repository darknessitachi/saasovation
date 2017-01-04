package com.abigdreamer.infinity.ddd.notification;

/**
 * 通知日志信息
 *  
 * @author Darkness
 * @date 2014-5-22 下午8:47:53
 * @version V1.0
 * @since ark 1.0
 */
class NotificationLogInfo {

    private NotificationLogId notificationLogId;// 通知日志ID
    private long totalLogged;// 日志总记录数

    public NotificationLogInfo(NotificationLogId aNotificationLogId, long aTotalLogged) {
        super();

        this.notificationLogId = aNotificationLogId;
        this.totalLogged = aTotalLogged;
    }

    public NotificationLogId notificationLogId() {
        return this.notificationLogId;
    }

    public long totalLogged() {
        return this.totalLogged;
    }
}
