package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemType;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.StoryPoints;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  产品待定项创建完成事件
 * 
 * @author Darkness
 * @date 2014-5-8 下午8:23:59 
 * @version V1.0
 */
public class ProductBacklogItemPlanned implements DomainEvent {

	private BacklogItemId backlogItemId;
	private String category;
	private int eventVersion;
	private Date occurredOn;
	private ProductId productId;
	private StoryPoints storyPoints;
	private String summary;
	private TenantId tenantId;
	private BacklogItemType type;

	public ProductBacklogItemPlanned(TenantId aTenantId, ProductId aProductId, BacklogItemId aBacklogItemId, String aSummary, String aCategory, BacklogItemType aType, StoryPoints aStoryPoints) {

		super();

		this.backlogItemId = aBacklogItemId;
		this.category = aCategory;
		this.eventVersion = 1;
		this.occurredOn = new Date();
		this.productId = aProductId;
		this.storyPoints = aStoryPoints;
		this.summary = aSummary;
		this.tenantId = aTenantId;
		this.type = aType;
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

	public ProductId productId() {
		return this.productId;
	}

	public StoryPoints storyPoints() {
		return this.storyPoints;
	}

	public String summary() {
		return this.summary;
	}

	public TenantId tenantId() {
		return this.tenantId;
	}

	public BacklogItemType type() {
		return this.type;
	}
}
