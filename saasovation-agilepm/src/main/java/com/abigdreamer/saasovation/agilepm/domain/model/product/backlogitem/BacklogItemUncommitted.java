package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 待定项从冲刺中回收完毕
 * @author Darkness
 * @date 2014-5-29 下午9:03:52
 * @version V1.0
 */
public class BacklogItemUncommitted implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;
    private SprintId uncommittedFromSprintId;

    public BacklogItemUncommitted(TenantId aTenantId, BacklogItemId aBacklogItemId, SprintId anUncommittedFromSprintId) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.uncommittedFromSprintId = anUncommittedFromSprintId;
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

    public SprintId uncommittedFromSprintId() {
        return this.uncommittedFromSprintId;
    }
}
