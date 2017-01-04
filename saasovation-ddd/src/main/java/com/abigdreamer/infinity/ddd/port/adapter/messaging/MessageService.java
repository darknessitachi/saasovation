package com.abigdreamer.infinity.ddd.port.adapter.messaging;

import java.util.ArrayList;
import java.util.List;

import com.abigdreamer.infinity.ddd.notification.Notification;
import com.abigdreamer.infinity.ddd.notification.NotificationSerializer;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.ConnectionSettings;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.Exchange;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.MessageParameters;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.MessageProducer;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.RabbitExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq.SlothClient;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq.SlothExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq.SlothExchangePublisher;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq.SlothServer;

/**
 * 消息服务
 * 
 * @author Darkness
 * @date 2014-12-18 下午7:14:41
 * @version V1.0
 */
public class MessageService {

	private static MessageServerType messageServerType = MessageServerType.Sloth;
	
	private static List<ExchangeListener>  exchangeListeners = new ArrayList<>();
	
	/**
	 * 启动消息服务器
	 * 
	 * @author Darkness
	 * @date 2014-12-18 下午7:16:34
	 * @version V1.0
	 * @throws Exception
	 */
	public static void start(MessageServerType messageServerType) throws Exception {
		
		MessageService.messageServerType = messageServerType;
		
		if (MessageServerType.Sloth == MessageService.messageServerType) {
			SlothServer.executeInProcessDetachedServer();// 启动消息服务器
			Thread.sleep(500L);
		} else if (MessageServerType.Rabbit == MessageService.messageServerType) {

		} 
	}

	/**
	 *  消息服务类型
	 * 
	 * @author Darkness
	 * @date 2014-12-18 下午7:20:04 
	 * @version V1.0
	 */
	public static enum MessageServerType {
		Sloth, Rabbit
	}

	public static void registerExchangeListener(ExchangeListener exchangeListener) {
		
		if (MessageServerType.Sloth == MessageService.messageServerType) {
			SlothExchangeListener realExchangeListener = new SlothExchangeListener(exchangeListener);
			SlothClient.instance().register(realExchangeListener);
			
			exchangeListeners.add(realExchangeListener);
		} else if (MessageServerType.Rabbit == MessageService.messageServerType) {
			RabbitExchangeListener realExchangeListener = new RabbitExchangeListener(exchangeListener);
			exchangeListeners.add(realExchangeListener);
		} 
		
	}

	public static void close() {
		if (MessageServerType.Sloth == MessageService.messageServerType) {
			for (ExchangeListener exchangeListener : exchangeListeners) {
				((SlothExchangeListener)exchangeListener).close();
			}
			SlothClient.instance().closeAll();
		} else if (MessageServerType.Rabbit == MessageService.messageServerType) {
			for (ExchangeListener exchangeListener : exchangeListeners) {
				((RabbitExchangeListener)exchangeListener).close();
			}
		} 
	}

	public static ExchangePublisher exchangePublisher(String exchangeName) {
		if (MessageServerType.Sloth == MessageService.messageServerType) {
			return new SlothExchangePublisher(exchangeName);
		}  else if (MessageServerType.Rabbit == MessageService.messageServerType) {
			return null;
		}
		return null;
	}
	
	public static void publish(String exchangeName, Notification aNotification) {
		if (MessageServerType.Sloth == MessageService.messageServerType) {
			String serializedNotification =
	                NotificationSerializer.instance().serialize(aNotification);

			exchangePublisher(exchangeName).publish(aNotification.typeName(), serializedNotification);
		}  else if (MessageServerType.Rabbit == MessageService.messageServerType) {
			MessageParameters messageParameters =
	                MessageParameters.durableTextParameters(
	                        aNotification.typeName(),
	                        Long.toString(aNotification.notificationId()),
	                        aNotification.occurredOn());

	        String serializedNotification =
	                NotificationSerializer.instance().serialize(aNotification);

	        messageProducer(exchangeName).send(serializedNotification, messageParameters);
		}
	}
	
    private static MessageProducer messageProducer(String exchangeName) {
        Exchange exchange =
            Exchange.fanOutInstance(
                    ConnectionSettings.instance(),
                    exchangeName,
                    true);

        MessageProducer messageProducer = MessageProducer.instance(exchange);

        return messageProducer;
    }

}
