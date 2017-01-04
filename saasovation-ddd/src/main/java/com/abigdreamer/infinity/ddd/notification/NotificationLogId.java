package com.abigdreamer.infinity.ddd.notification;

/**
 * 通知日志标识
 *  
 * @author Darkness
 * @date 2014-5-22 下午8:53:26
 * @version V1.0
 * @since ark 1.0
 */
public class NotificationLogId {

    private long low;
    private long high;

    /**
     * 编码通知日志Id，返回 "low,high"
     * 
     * @author Darkness
     * @date 2014-12-18 下午1:00:13 
     * @version V1.0
     */
    public static String encoded(NotificationLogId aNotificationLogId) {
        String encodedId = null;

        if (aNotificationLogId != null) {
            encodedId = aNotificationLogId.encoded();
        }

        return encodedId;
    }

    public static NotificationLogId first(int aNotificationsPerLog) {
        NotificationLogId id = new NotificationLogId(0, 0);

        return id.next(aNotificationsPerLog);
    }

    public NotificationLogId(long aLowId, long aHighId) {
        super();

        this.setLow(aLowId);
        this.setHigh(aHighId);
    }

    public NotificationLogId(String aNotificationLogId) {
        super();

        String[] textIds = aNotificationLogId.split(",");
        this.setLow(Long.parseLong(textIds[0]));
        this.setHigh(Long.parseLong(textIds[1]));
    }

    public String encoded() {
        return "" + this.low() + "," + this.high();
    }

    public long low() {
        return this.low;
    }

    public long high() {
        return this.high;
    }

    public NotificationLogId next(int aNotificationsPerLog) {
        long nextLow = this.high() + 1;

        // ensures a minted id value even though there may
        // not be this many notifications at present
        long nextHigh = nextLow + aNotificationsPerLog - 1;

        NotificationLogId next = new NotificationLogId(nextLow, nextHigh);

        if (this.equals(next)) {
            next = null;
        }

        return next;
    }

    public NotificationLogId previous(int aNotificationsPerLog) {
        long previousLow = Math.max(this.low() - aNotificationsPerLog, 1);

        long previousHigh = previousLow + aNotificationsPerLog - 1;

        NotificationLogId previous = new NotificationLogId(previousLow, previousHigh);

        if (this.equals(previous)) {
            previous = null;
        }

        return previous;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            NotificationLogId typedObject = (NotificationLogId) anObject;
            equalObjects =
                this.low() == typedObject.low() &&
                this.high() == typedObject.high();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (7919 * 29)
            + (int) this.low()
            + (int) this.high();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "NotificationLogId [low=" + low + ", high=" + high + "]";
    }

    private void setLow(long aLow) {
        this.low = aLow;
    }

    private void setHigh(long aHigh) {
        this.high = aHigh;
    }
}
