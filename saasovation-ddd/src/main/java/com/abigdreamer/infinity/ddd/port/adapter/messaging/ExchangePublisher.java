package com.abigdreamer.infinity.ddd.port.adapter.messaging;


/**   
 * 
 * @author Darkness
 * @date 2014-12-18 下午7:54:18 
 * @version V1.0   
 */
public interface ExchangePublisher {

	void publish(String aType, String aMessage);
	
	String exchangeName();

}
