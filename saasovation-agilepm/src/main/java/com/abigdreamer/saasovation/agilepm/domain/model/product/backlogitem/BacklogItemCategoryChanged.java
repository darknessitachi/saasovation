package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  待办项分类发生改变
 * 
 * @author Darkness
 * @date 2014-5-8 下午8:13:54 
 * @version V1.0
 */
public class BacklogItemCategoryChanged implements DomainEvent {

	private int eventVersion;
    private Date occurredOn;
    
    private BacklogItemId backlogItemId;
    private String category;
    private TenantId tenantId;

    public BacklogItemCategoryChanged(TenantId aTenantId, BacklogItemId aBacklogItemId, String aCategory) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.category = aCategory;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public String category() {
        return this.category;
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
