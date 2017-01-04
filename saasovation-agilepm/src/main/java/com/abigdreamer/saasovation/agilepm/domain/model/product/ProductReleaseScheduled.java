package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 产品发布日程计划完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午3:35:55
 * @version V1.0
 */
public class ProductReleaseScheduled implements DomainEvent {

    private Date begins;
    private String description;
    private int eventVersion;
    private Date ends;
    private String name;
    private Date occurredOn;
    private ProductId productId;
    private ReleaseId releaseId;
    private TenantId tenantId;

    public ProductReleaseScheduled(
            TenantId aTenantId,
            ProductId aProductId,
            ReleaseId aReleaseId,
            String aName,
            String aDescription,
            Date aBegins,
            Date anEnds) {

        super();

        this.begins = aBegins;
        this.description = aDescription;
        this.eventVersion = 1;
        this.ends = anEnds;
        this.name = aName;
        this.occurredOn = new Date();
        this.productId = aProductId;
        this.releaseId = aReleaseId;
        this.tenantId = aTenantId;
    }

    public Date begins() {
        return this.begins;
    }

    public String description() {
        return this.description;
    }

    public Date ends() {
        return this.ends;
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

    public ReleaseId releaseId() {
        return this.releaseId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
