package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * 客户端注册
 *  
 * @author Darkness
 * @date 2014-12-16 下午10:04:54
 * @version V1.0
 * @since ark 1.0
 */
public class ClientRegistration {

	private Set<String> exchanges;
	private InetAddress ipAddress;
	private int port;

	ClientRegistration(InetAddress anIPAddress, int aPort) {
		super();

		try {
	        this.exchanges = new HashSet<String>();
            this.ipAddress = anIPAddress == null ? InetAddress.getLocalHost() : anIPAddress;
            this.port = aPort;
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Cannot create client registration because unknown host.");
        }
	}

    ClientRegistration(int aPort) {
        this(null, aPort);
    }

    /**
     * 添加监听的扇区
     *  
     * @author Darkness
     * @date 2014-12-16 下午10:06:21
     * @version V1.0
     * @since ark 1.0
     */
	public void addSubscription(String anExchangeName) {
		System.out.println("ADDING EXCHANGE: " + anExchangeName);
		this.exchanges.add(anExchangeName);
	}

	/**
	 * 判断ip，端口是否匹配
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:07:02
	 * @version V1.0
	 * @since ark 1.0
	 */
	public boolean matches(InetAddress anIPAddress, int aPort) {
		return this.ipAddress.toString().equals(anIPAddress.toString()) && this.port == aPort;
	}

	/**
	 * 判断是否监听某扇区
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:07:22
	 * @version V1.0
	 * @since ark 1.0
	 */
	public boolean isSubscribedTo(String anExchangeName) {
		return this.exchanges.contains(anExchangeName);
	}

	/**
	 * ip地址
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:07:38
	 * @version V1.0
	 * @since ark 1.0
	 */
	public InetAddress ipAddress() {
		return this.ipAddress;
	}

	/**
	 * 端口
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:07:48
	 * @version V1.0
	 * @since ark 1.0
	 */
	public int port() {
		return this.port;
	}

	/**
	 * 移除某扇区的监听
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午10:07:56
	 * @version V1.0
	 * @since ark 1.0
	 */
	public void removeSubscription(String anExchangeName) {
		this.exchanges.remove(anExchangeName);
	}

	@Override
	public String toString() {
		return "ClientRegistration [ipAddress=" + this.ipAddress + ", port="
				+ this.port + ", exchanges=" + this.exchanges + "]";
	}
}
