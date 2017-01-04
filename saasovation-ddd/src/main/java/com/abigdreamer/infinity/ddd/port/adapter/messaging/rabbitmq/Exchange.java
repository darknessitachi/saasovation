package com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq;

import java.io.IOException;

import com.abigdreamer.infinity.ddd.port.adapter.messaging.MessageException;


/**
 *  交换器
 *  I am an exchange that simplifies RabbitMQ exchanges.
 *
 * @author Vaughn Vernon
 */
public class Exchange extends BrokerChannel {

    /** My type, which is the type of exchange. */
    private String type;

    /**
     * Answers a new instance of a direct Exchange with the name aName. The
     * underlying exchange has the isDurable quality, and is not auto-deleted.
     * @param aConnectionSettings the ConnectionSettings
     * @param aName the String name of the exchange
     * @param isDurable the boolean indicating whether or not I am durable
     * @return Exchange
     */
    public static Exchange directInstance(
            ConnectionSettings aConnectionSettings,
            String aName,
            boolean isDurable) {

        return new Exchange(aConnectionSettings, aName, "direct", isDurable);
    }

    /**
     * Answers a new instance of a fan-out Exchange with the name aName. The
     * underlying exchange has the isDurable quality, and is not auto-deleted.
     * @param aConnectionSettings the ConnectionSettings
     * @param aName the String name of the exchange
     * @param isDurable the boolean indicating whether or not I am durable
     * @return Exchange
     */
    public static Exchange fanOutInstance(
            ConnectionSettings aConnectionSettings,
            String aName,
            boolean isDurable) {

        return new Exchange(aConnectionSettings, aName, "fanout", isDurable);
    }

    /**
     * Answers a new instance of a headers Exchange with the name aName. The
     * underlying exchange has the isDurable quality, and is not auto-deleted.
     * @param aConnectionSettings the ConnectionSettings
     * @param aName the String name of the exchange
     * @param isDurable the boolean indicating whether or not I am durable
     * @return Exchange
     */
    public static Exchange headersInstance(
            ConnectionSettings aConnectionSettings,
            String aName,
            boolean isDurable) {

        return new Exchange(aConnectionSettings, aName, "headers", isDurable);
    }

    /**
     * Answers a new instance of a topic Exchange with the name aName. The
     * underlying exchange has the isDurable quality, and is not auto-deleted.
     * @param aConnectionSettings the ConnectionSettings
     * @param aName the String name of the exchange
     * @param isDurable the boolean indicating whether or not I am durable
     * @return Exchange
     */
    public static Exchange topicInstance(
            ConnectionSettings aConnectionSettings,
            String aName,
            boolean isDurable) {

        return new Exchange(aConnectionSettings, aName, "topic", isDurable);
    }

    /**
     * Constructs my default state.
     * @param aConnectionSettings the ConnectionSettings
     * @param aName the String name of the exchange
     * @param aType the String type of the exchange
     * @param isDurable the boolean indicating whether or not I am durable
     */
    protected Exchange(
            ConnectionSettings aConnectionSettings,
            String aName,
            String aType,
            boolean isDurable) {

        super(aConnectionSettings, aName);

        this.setDurable(isDurable);

        this.setType(aType);

        try {
            this.channel().exchangeDeclare(aName, aType, isDurable);
        } catch (IOException e) {
            throw new MessageException("Failed to create/open the exchange.", e);
        }
    }

    /**
     * @see org.infinite.common.ddd.port.adapter.messaging.rabbitmq.BrokerChannel#isExchange()
     */
    @Override
    protected boolean isExchange() {
        return true;
    }

    /**
     * Answers my type.
     * @return String
     */
    protected String type() {
        return this.type;
    }

    /**
     * Sets my type.
     * @param aType the String to set as my type
     */
    private void setType(String aType) {
        this.type = aType;
    }
}
