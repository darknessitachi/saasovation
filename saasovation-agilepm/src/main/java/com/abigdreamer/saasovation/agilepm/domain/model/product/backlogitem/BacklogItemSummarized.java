package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 设置待办项摘要完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:58:27
 * @version V1.0
 */
public class BacklogItemSummarized implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private String summary;
    private TenantId tenantId;

    public BacklogItemSummarized(TenantId aTenantId, BacklogItemId aBacklogItemId, String aSummary) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.summary = aSummary;
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

    public String summary() {
        return this.summary;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
