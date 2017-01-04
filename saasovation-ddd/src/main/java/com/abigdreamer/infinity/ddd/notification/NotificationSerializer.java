package com.abigdreamer.infinity.ddd.notification;

import com.abigdreamer.infinity.common.serializer.AbstractSerializer;

public class NotificationSerializer extends AbstractSerializer {

    private static NotificationSerializer notificationSerializer;

    public static synchronized NotificationSerializer instance() {
        if (NotificationSerializer.notificationSerializer == null) {
            NotificationSerializer.notificationSerializer = new NotificationSerializer();
        }

        return NotificationSerializer.notificationSerializer;
    }

    public NotificationSerializer(boolean isCompact) {
        this(false, isCompact);
    }

    public NotificationSerializer(boolean isPretty, boolean isCompact) {
        super(isPretty, isCompact);
    }

    public String serialize(Notification aNotification) {
        String serialization = this.gson().toJson(aNotification);

        return serialization;
    }

    public <T extends Notification> T deserialize(String aSerialization, final Class<T> aType) {
        T notification = this.gson().fromJson(aSerialization, aType);

        return notification;
    }

    private NotificationSerializer() {
        this(false, false);
    }
}
