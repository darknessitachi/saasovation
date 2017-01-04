package com.abigdreamer.infinity.ddd.port.adapter.messaging;
/**   
 * 
 * @author Darkness
 * @date 2014-12-18 下午7:30:18 
 * @version V1.0   
 */
public interface ExchangeListener {
    
	/**
	 * 监听名称
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:08:50
	 * @version V1.0
	 * @since ark 1.0
	 */
	String name();
	
	/**
	 * 监听的扇区名称
	 * 
	 *  Answers the String name of the exchange I listen to.
     * @return Strin
     * g
	 * @author Darkness
	 * @date 2014-12-16 下午11:07:34
	 * @version V1.0
	 * @since ark 1.0
     */
	String exchangeName();

	/**
	 * 派发消息
     * Filters out unwanted events and dispatches ones of interest.
     * @param aType the String message type
     * @param aTextMessage the String raw text message being handled
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:07:45
	 * @version V1.0
	 * @since ark 1.0
	 */
	void filteredDispatch(String aType, String aTextMessage);

	/**
	 * 监听的消息类型
     * Answers the kinds of messages I listen to.
     * @return String[]
	 *  
	 * @author Darkness
	 * @date 2014-12-16 下午11:08:02
	 * @version V1.0
	 * @since ark 1.0
	 */
	String[] listensTo();
	
}
