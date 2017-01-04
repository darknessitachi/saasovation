package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


public class BacklogItemDiscussionInitiated implements DomainEvent {

    private BacklogItemId backlogItemId;
    private BacklogItemDiscussion discussion;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;

    public BacklogItemDiscussionInitiated(TenantId aTenantId, BacklogItemId aBacklogItemId, BacklogItemDiscussion aDiscussion) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.discussion = aDiscussion;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public BacklogItemDiscussion discussion() {
        return this.discussion;
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