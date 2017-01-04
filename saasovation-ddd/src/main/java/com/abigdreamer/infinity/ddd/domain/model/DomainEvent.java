package com.abigdreamer.infinity.ddd.domain.model;

import java.util.Date;

/**
 *  领域事件
 * 
 * @author Darkness
 * @date 2014-5-5 下午4:52:14 
 * @version V1.0
 */
public interface DomainEvent {

	int eventVersion();

	/**
	 *  事件发生的时间
	 * 
	 * @author Darkness
	 * @date 2014-5-5 下午4:52:35 
	 * @version V1.0
	 */
	Date occurredOn();
}
