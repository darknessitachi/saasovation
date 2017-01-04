package com.abigdreamer.infinity.ddd.notification;

import java.math.BigDecimal;
import java.util.Date;

import com.abigdreamer.infinity.ddd.media.AbstractJSONMediaReader;
import com.google.gson.JsonObject;

public class NotificationReader extends AbstractJSONMediaReader {

    private JsonObject event;

    public NotificationReader(String aJSONNotification) {
        super(aJSONNotification);

        this.setEvent(this.representation().get("event").getAsJsonObject());
    }

    public NotificationReader(JsonObject aRepresentationObject) {
        super(aRepresentationObject);

        this.setEvent(this.representation().get("event").getAsJsonObject());
    }

    public BigDecimal eventBigDecimalValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : new BigDecimal(stringValue);
    }

    public Boolean eventBooleanValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Boolean.parseBoolean(stringValue);
    }

    public Date eventDateValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : new Date(Long.parseLong(stringValue));
    }

    public Double eventDoubleValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Double.parseDouble(stringValue);
    }

    public Float eventFloatValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Float.parseFloat(stringValue);
    }

    public Integer eventIntegerValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Integer.parseInt(stringValue);
    }

    public Long eventLongValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Long.parseLong(stringValue);
    }

    public String eventStringValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue;
    }

    public long notificationId() {
        long notificationId = this.longValue("notificationId");

        return notificationId;
    }

    public String notificationIdAsString() {
        String notificationId = this.stringValue("notificationId");

        return notificationId;
    }

    public Date occurredOn() {
        long time = this.longValue("occurredOn");

        return new Date(time);
    }

    public String typeName() {
        String typeName = this.stringValue("typeName");

        return typeName;
    }

    public int version() {
        int version = this.integerValue("version");

        return version;
    }

    private JsonObject event() {
        return this.event;
    }

    private void setEvent(JsonObject anEvent) {
        this.event = anEvent;
    }
}
