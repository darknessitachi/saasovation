package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;

/**
 * 扇区监听
 *  
 * @author Darkness
 * @date 2014-12-16 下午11:06:18
 * @version V1.0
 * @since ark 1.0
 */
public class SlothExchangeListener implements ExchangeListener {

	ExchangeListener exchangeListener;
	
	private Set<String> messageTypes;

	public SlothExchangeListener(ExchangeListener exchangeListener) {
		super();
		
		this.exchangeListener = exchangeListener;
		
		this.establishMessageTypes();
	}

	/**
	 * 关闭扇区监听，取消客户端监听
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:06:47
	 * @version V1.0
	 * @since ark 1.0
	 */
	public void close() {
		SlothClient.instance().unregister(this);
	}

	/**
	 * 判断是否监听某消息类别
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:10:51
	 * @version V1.0
	 * @since ark 1.0
	 */
	protected boolean listensTo(String aType) {
		Set<String> types = this.listensToMessageTypes();

		return types.isEmpty() || types.contains(aType);
	}

	/**
	 * 组装监听的消息类别
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:08:35
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void establishMessageTypes() {
		String[] filterOutAllBut = this.exchangeListener.listensTo();

		if (filterOutAllBut == null) {
			filterOutAllBut = new String[0];
		}

		this.setMessageTypes(new HashSet<String>(Arrays.asList(filterOutAllBut)));
	}

	/**
	 * 获取监听的消息类别
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:10:27
	 * @version V1.0
	 * @since ark 1.0
	 */
	private Set<String> listensToMessageTypes() {
		return this.messageTypes;
	}

	/**
	 * 设置监听的消息类别
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:10:09
	 * @version V1.0
	 * @since ark 1.0
	 */
	private void setMessageTypes(HashSet<String> aMessageTypes) {
		this.messageTypes = aMessageTypes;
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
