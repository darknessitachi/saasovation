package com.abigdreamer.infinity.ddd.port.adapter.messaging.slothmq;
/**   
 * 
 * @author Darkness
 * @date 2014-12-16 下午7:21:27 
 * @version V1.0   
 */
public interface MessageType {

	String ATTACH = "ATTACH:";
	String SUBSCRIBE = "SUBSCRIBE:";
	String PUBLISH = "PUBLISH:";
	String CLOSE = "CLOSE:";
	String UNSUBSCRIBE = "UNSUBSCRIBE:";

}
