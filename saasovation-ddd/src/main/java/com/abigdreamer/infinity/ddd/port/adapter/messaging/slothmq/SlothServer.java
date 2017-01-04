package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息服务器
 *  
 * @author Darkness
 * @date 2014-12-16 下午10:32:19
 * @version V1.0
 * @since ark 1.0
 */
public class SlothServer extends SlothWorker {

	private Map<Integer,ClientRegistration> clientRegistrations;

	/**
	 * 开启一个新的线程启动消息服务器
	 * 
	 * @author Darkness
	 * @date 2014-12-16 下午7:11:17 
	 * @version V1.0
	 */
	public static void executeInProcessDetachedServer() {
		Thread serverThread = new Thread() {
			@Override
			public void run() {
				SlothServer.executeNewServer();
			}
		};

		serverThread.start();
	}

	/**
	 * 启动新的消息服务器
	 * 
	 * @author Darkness
	 * @date 2014-12-16 下午7:12:05 
	 * @version V1.0
	 */
	public static void executeNewServer() {
		SlothServer slothServer = new SlothServer();

		slothServer.execute();
	}

	public static void main(String anArguments[]) throws Exception {
		SlothServer.executeNewServer();
	}

	public SlothServer() {
		super();

		this.clientRegistrations = new HashMap<Integer,ClientRegistration>();
	}

	/**
	 * 启动服务器接收消息
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:02:22
	 * @version V1.0
	 * @since ark 1.0
	 */
	public void execute() {

		while (!this.isClosed()) {
		    String receivedData = this.receive();

		    if (receivedData != null) {
		        this.handleMessage(receivedData);
		    }
		}
	}

	@Override
	protected boolean slothHub() {
	    return true;
	}

	/**
	 * 添加客户端
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:03:30
	 * @version V1.0
	 * @since ark 1.0
	 */
	private ClientRegistration attach(String aReceivedData) {
		int port = Integer.parseInt(aReceivedData.substring(MessageType.ATTACH.length()));

		return this.attach(port);
	}

	/**
	 * 添加客户端
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:04:13
	 * @version V1.0
	 * @since ark 1.0
	 */
    private ClientRegistration attach(int aPort) {
        ClientRegistration clientRegistration = this.clientRegistrations.get(aPort);

        if (clientRegistration == null) {
            clientRegistration = new ClientRegistration(aPort);
            this.clientRegistrations.put(aPort, clientRegistration);
        }

        return clientRegistration;
    }

    /**
     * 处理消息
     *  
     * @author Darkness
     * @date 2014-12-16 下午10:08:38
     * @version V1.0
     * @since ark 1.0
     */
	private void handleMessage(String aReceivedData) {
		System.out.println("SLOTH SERVER: Handling: " + aReceivedData);

		if (aReceivedData.startsWith(MessageType.ATTACH)) {
			this.attach(aReceivedData);
		} else if (aReceivedData.startsWith(MessageType.CLOSE)) {
			this.close();
		} else if (aReceivedData.startsWith(MessageType.PUBLISH)) {
			this.publishToClients(aReceivedData);
		} else if (aReceivedData.startsWith(MessageType.SUBSCRIBE)) {
			this.subscribeClientTo(aReceivedData.substring(MessageType.SUBSCRIBE.length()));
		} else if (aReceivedData.startsWith(MessageType.UNSUBSCRIBE)) {
			this.unsubscribeClientFrom(aReceivedData.substring(MessageType.UNSUBSCRIBE.length()));
		} else {
			System.out.println("SLOTH SERVER: Does not understand: " + aReceivedData);
		}
	}

	/**
	 * 发布消息至客户端
	 *  
	 * @param anExchangeMessage MessageType.PUBLISH + anExchangeName + "TYPE:" + aType + "MSG:" + aMessage;
	 * @author Darkness
	 * @date 2014-12-16 下午10:11:00
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void publishToClients(String anExchangeMessage) {

		int exchangeDivider = anExchangeMessage.indexOf(MessageType.PUBLISH);
		int typeDivider = anExchangeMessage.indexOf("TYPE:", exchangeDivider + MessageType.PUBLISH.length());

		if (exchangeDivider == -1) {
			System.out.println("SLOTH SERVER: PUBLISH: No exchange name; ignoring: " + anExchangeMessage);
		} else if (typeDivider == -1) {
			System.out.println("SLOTH SERVER: PUBLISH: No TYPE; ignoring: " + anExchangeMessage);
		} else {
			String exchangeName = anExchangeMessage.substring(exchangeDivider + MessageType.PUBLISH.length(), typeDivider);

			for (ClientRegistration clientSubscriptions : this.clientRegistrations.values()) {
				if (clientSubscriptions.isSubscribedTo(exchangeName)) {
					this.sendTo(clientSubscriptions.port(), anExchangeMessage);
				}
			}
		}
	}

	/**
	 * 注册客户端监听某扇区
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:23:55
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void subscribeClientTo(String aPortWithExchangeName) {
	    String[] parts = aPortWithExchangeName.split(":");
		int port = Integer.parseInt(parts[0]);
		String exchangeName = parts[1];

		ClientRegistration clientRegistration = this.clientRegistrations.get(port);

		if (clientRegistration == null) {
			clientRegistration = this.attach(port);
		}

		clientRegistration.addSubscription(exchangeName);

		System.out.println("SLOTH SERVER: Subscribed: " + clientRegistration + " TO: " + exchangeName);
	}

	/**
	 * 注册客户端取消监听某扇区
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:30:06
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void unsubscribeClientFrom(String aPortWithExchangeName) {
        String[] parts = aPortWithExchangeName.split(":");
        int port = Integer.parseInt(parts[0]);
        String exchangeName = parts[1];

		ClientRegistration clientRegistration = this.clientRegistrations.get(port);

		if (clientRegistration != null) {
			clientRegistration.removeSubscription(exchangeName);

			System.out.println("SLOTH SERVER: Unsubscribed: " + clientRegistration + " FROM: " + exchangeName);
		}
	}
}
