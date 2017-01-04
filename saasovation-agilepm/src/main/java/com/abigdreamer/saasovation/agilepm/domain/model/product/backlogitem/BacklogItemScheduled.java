package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 待定项日程计划完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:06:18
 * @version V1.0
 */
public class BacklogItemScheduled implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private ReleaseId scheduledForReleaseId;
    private TenantId tenantId;

    public BacklogItemScheduled(TenantId aTenantId, BacklogItemId aBacklogItemId, ReleaseId aScheduledForReleaseId) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.scheduledForReleaseId = aScheduledForReleaseId;
        this.tenantId = aTenantId;
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

    public ReleaseId scheduledForReleaseId() {
        return this.scheduledForReleaseId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
