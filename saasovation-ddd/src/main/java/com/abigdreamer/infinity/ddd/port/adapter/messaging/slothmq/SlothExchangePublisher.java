package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;

import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangePublisher;

/**
 * 扇区发布器
 *  
 * @author Darkness
 * @date 2014-12-16 下午11:01:39
 * @version V1.0
 * @since ark 1.0
 */
public class SlothExchangePublisher implements ExchangePublisher {

	private String exchangeName;

	public SlothExchangePublisher(String anExchangeName) {
		super();

		this.exchangeName = anExchangeName;
	}

	/**
	 * 发布消息
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:02:22
	 * @version V1.0
	 * @since ark 1.0
	 */
	@Override
	public void publish(String aType, String aMessage) {
		SlothClient.instance().publish(this.exchangeName(), aType, aMessage);
	}

	/**
	 * 扇区名称
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:04:30
	 * @version V1.0
	 * @since ark 1.0
	 */
	@Override
	public String exchangeName() {
		return this.exchangeName;
	}
}
