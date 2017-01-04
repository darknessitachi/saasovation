package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  待定项提交完毕
 * 
 * @author Darkness
 * @date 2014-5-5 下午4:49:19 
 * @version V1.0
 */
public class BacklogItemCommitted implements DomainEvent {

    private BacklogItemId backlogItemId;// 待定项ID
    private SprintId committedToSprintId;// 冲刺ID
    private TenantId tenantId;
    
    private int eventVersion;
    private Date occurredOn;

    public BacklogItemCommitted(TenantId aTenantId, BacklogItemId aBacklogItemId, SprintId aCommittedToSprintId) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.committedToSprintId = aCommittedToSprintId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public SprintId committedToSprintId() {
        return this.committedToSprintId;
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
