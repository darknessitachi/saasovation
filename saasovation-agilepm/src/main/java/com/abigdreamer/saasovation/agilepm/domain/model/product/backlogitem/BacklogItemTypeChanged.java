package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 待办项类别发生改变
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:45:55
 * @version V1.0
 */
public class BacklogItemTypeChanged implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;
    private BacklogItemType type;

    public BacklogItemTypeChanged(TenantId aTenantId, BacklogItemId aBacklogItemId, BacklogItemType aType) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.type = aType;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
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

    public BacklogItemType type() {
        return this.type;
    }
}
