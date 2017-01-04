package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 取消发布计划日程
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:04:49
 * @version V1.0
 */
public class BacklogItemUnscheduled implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;
    private ReleaseId unscheduledForReleaseId;

    public BacklogItemUnscheduled(TenantId aTenantId, BacklogItemId aBacklogItemId, ReleaseId anUnscheduledForReleaseId) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.unscheduledForReleaseId = anUnscheduledForReleaseId;
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

    public ReleaseId unscheduledForReleaseId() {
        return this.unscheduledForReleaseId;
    }
}
