package com.abigdreamer.infinity.ddd.port.adapter.messaging;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

/**
 * 电话号码流程事件
 *  
 * @author Darkness
 * @date 2014-12-17 下午9:37:35
 * @version V1.0
 * @since ark 1.0
 */
public abstract class PhoneNumberProcessEvent implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    
    private String processId;

    public PhoneNumberProcessEvent(String aProcessId) {
        super();

        this.eventVersion = 1;
        this.occurredOn = new Date();
        
        this.processId = aProcessId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public String processId() {
        return this.processId;
    }
}
