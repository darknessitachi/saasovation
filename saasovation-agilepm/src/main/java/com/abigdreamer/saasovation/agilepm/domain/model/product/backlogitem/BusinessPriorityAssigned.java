package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 指定业务价值完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:32:16
 * @version V1.0
 */
public class BusinessPriorityAssigned implements DomainEvent {

    private BacklogItemId backlogItemId;
    private BusinessPriority businessPriority;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;

    public BusinessPriorityAssigned(TenantId aTenantId, BacklogItemId aBacklogItemId, BusinessPriority aBusinessPriority) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.businessPriority = aBusinessPriority;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public BusinessPriority businessPriority() {
        return this.businessPriority;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
