package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwnerId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 产品讨论请求完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午3:33:54
 * @version V1.0
 */
public class ProductDiscussionRequested implements DomainEvent {

    private String description;
    private int eventVersion;
    private String name;
    private Date occurredOn;
    private ProductId productId;
    private ProductOwnerId productOwnerId;
    private boolean requestingDiscussion;
    private TenantId tenantId;

    public ProductDiscussionRequested(
            TenantId aTenantId,
            ProductId aProductId,
            ProductOwnerId aProductOwnerId,
            String aName,
            String aDescription,
            boolean aRequestingDiscussion) {

        super();

        this.description = aDescription;
        this.eventVersion = 1;
        this.name = aName;
        this.occurredOn = new Date();
        this.productId = aProductId;
        this.productOwnerId = aProductOwnerId;
        this.requestingDiscussion = aRequestingDiscussion;
        this.tenantId = aTenantId;
    }

    public String description() {
        return this.description;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String name() {
        return this.name;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public ProductId productId() {
        return this.productId;
    }

    public ProductOwnerId productOwnerId() {
        return this.productOwnerId;
    }

    public boolean isRequestingDiscussion() {
        return this.requestingDiscussion;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
