package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息客户端
 *  
 * @author Darkness
 * @date 2014-12-16 下午10:32:33
 * @version V1.0
 * @since ark 1.0
 */
public class SlothClient extends SlothWorker {

	private static SlothClient instance;

	private Map<String,SlothExchangeListener> exchangeListeners;
	private Object lock;

	/**
	 * 返回消息客户端实例
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:33:05
	 * @version V1.0
	 * @since ark 1.0
	 */
	public static synchronized SlothClient instance() {
		if (instance == null) {
			instance = new SlothClient();
		}
		return instance;
	}
	
	private SlothClient() {
		super();

		this.exchangeListeners = new HashMap<String,SlothExchangeListener>();
		this.lock = new Object();

		this.attach();
		this.receiveAll();
	}

	/**
	 * 关闭客户端，取消所有监听
	 */
	@Override
	public void close() {
		System.out.println("SLOTH CLIENT: Closing...");

		super.close();

		List<SlothExchangeListener> listeners =
				new ArrayList<SlothExchangeListener>(this.exchangeListeners.values());

		for (SlothExchangeListener listener : listeners) {
			this.unregister(listener);
		}

		System.out.println("SLOTH CLIENT: Closed.");
	}

	/**
	 * 关闭客户端&服务器
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:36:00
	 * @version V1.0
	 * @since ark 1.0
	 */
	public void closeAll() {
		instance = null;

		this.close();

		this.sendToServer(MessageType.CLOSE);
	}

	/**
	 * 发布消息
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:36:44
	 * @version V1.0
	 * @since ark 1.0
	 */
	public void publish(String anExchangeName, String aType, String aMessage) {
		String encodedMessage = MessageType.PUBLISH + anExchangeName + "TYPE:" + aType + "MSG:" + aMessage;

        this.sendToServer(encodedMessage);
	}

	/**
	 * 注册监听
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:39:07
	 * @version V1.0
	 * @since ark 1.0
	 */
	public void register(SlothExchangeListener anExchangeListener) {
		synchronized (lock) {
			this.exchangeListeners.put(anExchangeListener.name(), anExchangeListener);
		}

		this.sendToServer(MessageType.SUBSCRIBE + this.port() + ":" + anExchangeListener.exchangeName());
	}

	/**
	 * 取消注册监听
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:39:46
	 * @version V1.0
	 * @since ark 1.0
	 */
	public void unregister(SlothExchangeListener anExchangeListener) {
		synchronized (lock) {
			this.exchangeListeners.remove(anExchangeListener.name());
		}

		this.sendToServer(MessageType.UNSUBSCRIBE + this.port() + ":" + anExchangeListener.exchangeName());
	}

	/**
	 * 附加到服务器的客户端注册器中
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:45:49
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void attach() {
        this.sendToServer(MessageType.ATTACH + this.port());
	}

	/**
	 * 分发消息到扇区监听
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:56:11
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void dispatchMessage(String anEncodedMessage) {
		int exchangeDivider = anEncodedMessage.indexOf(MessageType.PUBLISH);
		int typeDivider = anEncodedMessage.indexOf("TYPE:", exchangeDivider + 8);
		int msgDivider = anEncodedMessage.indexOf("MSG:", typeDivider + 5);

		String exchangeName = anEncodedMessage.substring(exchangeDivider + 8, typeDivider);
		String type = anEncodedMessage.substring(typeDivider + 5, msgDivider);
		String message = anEncodedMessage.substring(msgDivider + 4);

		List<SlothExchangeListener> listeners = null;

		synchronized (lock) {
			listeners = new ArrayList<SlothExchangeListener>(this.exchangeListeners.values());
		}

		for (SlothExchangeListener listener : listeners) {
			if (listener.exchangeName().equals(exchangeName) && listener.listensTo(type)) {
				try {
					System.out.println("SLOTH CLIENT: Dispatching: Exchange: " + exchangeName + " Type: " + type + " Msg: " + message);

					listener.filteredDispatch(type, message);
				} catch (Exception e) {
					System.out.println("SLOTH CLIENT: Exception while dispatching message: "
							+ e.getMessage() + ": " + anEncodedMessage);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 接收消息
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:55:49
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void receiveAll() {
		Thread receiverThread = new Thread() {
			@Override
			public void run() {
				while (!isClosed()) {
					String receivedData = null;

                    synchronized (lock) {
                        receivedData = receive();
                    }

					if (receivedData != null) {
						dispatchMessage(receivedData.trim());
					} else {
                        sleepFor(10L);
					}
				}
			}
		};

		receiverThread.start();
	}
}
