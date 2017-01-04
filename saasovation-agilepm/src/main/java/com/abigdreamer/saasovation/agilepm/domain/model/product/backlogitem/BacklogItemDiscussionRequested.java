package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 待办项讨论申请完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:50:25
 * @version V1.0
 */
public class BacklogItemDiscussionRequested implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private ProductId productId;
    private boolean requestingDiscussion;
    private TenantId tenantId;

    public BacklogItemDiscussionRequested(
            TenantId aTenantId,
            ProductId aProductId,
            BacklogItemId aBacklogItemId,
            boolean isRequestingDiscussion) {

        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.productId = aProductId;
        this.requestingDiscussion = isRequestingDiscussion;
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

    public ProductId productId() {
        return this.productId;
    }

    public boolean isRequestingDiscussion() {
        return this.requestingDiscussion;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
