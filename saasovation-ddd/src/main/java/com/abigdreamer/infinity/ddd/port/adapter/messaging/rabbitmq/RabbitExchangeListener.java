package com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq;

import java.util.Date;

import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;

/**
 * I am an abstract base class for exchange listeners.
 * I perform the basic set up according to the answers
 * from my concrete subclass.
 *
 * @author Vaughn Vernon
 */
public class RabbitExchangeListener implements ExchangeListener {

    private MessageConsumer messageConsumer;

    private Queue queue;
    
    private ExchangeListener exchangeListener;

    /**
     * Constructs my default state.
     */
    public RabbitExchangeListener(ExchangeListener exchangeListener) {
        super();
        
        this.exchangeListener = exchangeListener;

        this.attachToQueue();

        this.registerConsumer();
    }

    /**
     * Closes my queue.
     */
    public void close() {
        this.queue().close();
    }

    /**
     * Answers the String name of the queue I listen to. By
     * default it is the simple name of my concrete class.
     * May be overridden to change the name.
     * @return String
     */
    protected String queueName() {
        return this.exchangeListener.name();
    }

    /**
     * Attaches to the queues I listen to for messages.
     */
    private void attachToQueue() {
        Exchange exchange =
                Exchange.fanOutInstance(
                        ConnectionSettings.instance(),
                        this.exchangeName(),
                        true);

        this.queue =
                Queue.individualExchangeSubscriberInstance(
                        exchange,
                        this.exchangeName() + "." + this.queueName());
    }

    /**
     * Answers my queue.
     * @return Queue
     */
    private Queue queue() {
        return this.queue;
    }

    /**
     * Registers my listener for queue messages and dispatching.
     */
    private void registerConsumer() {
        this.messageConsumer = MessageConsumer.instance(this.queue(), false);

        this.messageConsumer.receiveOnly(
                this.listensTo(),
                new MessageListener(MessageListener.Type.TEXT) {

            @Override
            public void handleMessage(
                    String aType,
                    String aMessageId,
                    Date aTimestamp,
                    String aTextMessage,
                    long aDeliveryTag,
                    boolean isRedelivery)
            throws Exception {
                filteredDispatch(aType, aTextMessage);
            }
        });
    }

	@Override
	public String name() {
		return exchangeListener.name();
	}

	@Override
	public String exchangeName() {
		return exchangeListener.exchangeName();
	}

	@Override
	public void filteredDispatch(String aType, String aTextMessage) {
		exchangeListener.filteredDispatch(aType, aTextMessage);
	}

	@Override
	public String[] listensTo() {
		return exchangeListener.listensTo();
	}
}
