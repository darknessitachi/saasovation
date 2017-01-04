package com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq;

import java.util.Date;

import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * I am a set of message parameters.
 *
 * @author Vaughn Vernon
 */
public class MessageParameters {

    private BasicProperties properties;

    public static MessageParameters durableTextParameters(
            String aType,
            String aMessageId,
            Date aTimestamp) {

        BasicProperties properties =
            new BasicProperties(
                    "text/plain",   // contentType
                    null,           // contentEncoding
                    null,           // headers
                    2,              // deliveryMode, persistent
                    0,              // priority
                    null,           // correlationId
                    null,           // replyTo
                    null,           // expiration
                    aMessageId,     // messageId
                    aTimestamp,     // timestamp
                    aType,          // type
                    null,           // userId
                    null,           // appId
                    null);          // clusterId

        return new MessageParameters(properties);
    }

    public static MessageParameters textParameters(
            String aType,
            String aMessageId,
            Date aTimestamp) {

        BasicProperties properties =
            new BasicProperties(
                    "text/plain",   // contentType
                    null,           // contentEncoding
                    null,           // headers
                    1,              // deliveryMode, non-persistent
                    0,              // priority
                    null,           // correlationId
                    null,           // replyTo
                    null,           // expiration
                    aMessageId,     // messageId
                    aTimestamp,     // timestamp
                    aType,          // type
                    null,           // userId
                    null,           // appId
                    null);          // clusterId

        return new MessageParameters(properties);
    }

    public boolean isDurable() {
        Integer deliveryMode = this.properties().getDeliveryMode();

        return (deliveryMode != null && deliveryMode == 2);
    }

    protected BasicProperties properties() {
        return this.properties;
    }

    private MessageParameters(BasicProperties aProperties) {
        super();
        this.properties(aProperties);
    }

    private void properties(BasicProperties aProperties) {
        this.properties = aProperties;
    }
}
