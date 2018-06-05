package com.abigdreamer.infinity.ddd.notification;

import java.io.Serializable;

import com.rapidark.framework.commons.lang.AssertionConcern;


public class PublishedNotificationTracker extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private long publishedNotificationTrackerId;
    private int concurrencyVersion;
    
    private long mostRecentPublishedNotificationId;
    private String typeName;

    public PublishedNotificationTracker(String aTypeName) {
        this();

        this.setTypeName(aTypeName);
    }

    public void failWhenConcurrencyViolation(int aVersion) {
        this.assertStateTrue(
                aVersion == this.concurrencyVersion(),
                "Concurrency Violation: Stale data detected. Entity was already modified.");
    }

    public long mostRecentPublishedNotificationId() {
        return this.mostRecentPublishedNotificationId;
    }

    public void setMostRecentPublishedNotificationId(long aMostRecentPublishedNotificationId) {
        this.mostRecentPublishedNotificationId = aMostRecentPublishedNotificationId;
    }

    public long publishedNotificationTrackerId() {
        return this.publishedNotificationTrackerId;
    }

    public String typeName() {
        return this.typeName;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            PublishedNotificationTracker typedObject = (PublishedNotificationTracker) anObject;
            equalObjects =
                this.publishedNotificationTrackerId() == typedObject.publishedNotificationTrackerId() &&
                this.typeName().equals(typedObject.typeName()) &&
                this.mostRecentPublishedNotificationId() == typedObject.mostRecentPublishedNotificationId();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (11575 * 241)
            + (int) this.publishedNotificationTrackerId()
            + (int) this.mostRecentPublishedNotificationId()
            + this.typeName().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "PublishedNotificationTracker [mostRecentPublishedNotificationId=" + mostRecentPublishedNotificationId
                + ", publishedNotificationTrackerId=" + publishedNotificationTrackerId + ", typeName=" + typeName + "]";
    }

    protected PublishedNotificationTracker() {
        super();
    }

    protected int concurrencyVersion() {
        return this.concurrencyVersion;
    }

    protected void setConcurrencyVersion(int aConcurrencyVersion) {
        this.concurrencyVersion = aConcurrencyVersion;
    }

    protected void setPublishedNotificationTrackerId(long aPublishedNotificationTrackerId) {
        this.publishedNotificationTrackerId = aPublishedNotificationTrackerId;
    }

    protected void setTypeName(String aTypeName) {
        this.assertArgumentNotEmpty(aTypeName, "The tracker type name is required.");
        this.assertArgumentLength(aTypeName, 100, "The tracker type name must be 100 characters or less.");

        this.typeName = aTypeName;
    }
}
