package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 讨论初始化完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午3:32:14
 * @version V1.0
 */
public class ProductDiscussionInitiated implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private ProductDiscussion productDiscussion;
    private ProductId productId;
    private TenantId tenantId;

    public ProductDiscussionInitiated(
            TenantId aTenantId,
            ProductId aProductId,
            ProductDiscussion aProductDiscussion) {

        super();

        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.productDiscussion = aProductDiscussion;
        this.productId = aProductId;
        this.tenantId = aTenantId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public ProductDiscussion productDiscussion() {
        return this.productDiscussion;
    }

    public ProductId productId() {
        return this.productId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
