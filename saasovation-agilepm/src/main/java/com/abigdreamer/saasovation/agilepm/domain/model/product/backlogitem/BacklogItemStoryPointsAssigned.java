package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 待办项故事点分配完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:34:39
 * @version V1.0
 */
public class BacklogItemStoryPointsAssigned implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private StoryPoints storyPoints;
    private TenantId tenantId;

    public BacklogItemStoryPointsAssigned(TenantId aTenantId, BacklogItemId aBacklogItemId, StoryPoints aStoryPoints) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.storyPoints = aStoryPoints;
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

    public StoryPoints storyPoints() {
        return this.storyPoints;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
