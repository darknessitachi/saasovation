package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 产品计划冲刺完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:28:28
 * @version V1.0
 */
public class ProductSprintScheduled implements DomainEvent {

	private int eventVersion;
	private Date occurredOn;

	private Date begins;
	private Date ends;
	private String goals;
	private String name;
	private ProductId productId;
	private SprintId sprintId;
	private TenantId tenantId;

	public ProductSprintScheduled(TenantId aTenantId, ProductId aProductId, SprintId aSprintId, String aName, String aGoals, Date aBegins, Date anEnds) {

		super();

		this.begins = aBegins;
		this.ends = anEnds;
		this.eventVersion = 1;
		this.goals = aGoals;
		this.name = aName;
		this.occurredOn = new Date();
		this.productId = aProductId;
		this.sprintId = aSprintId;
		this.tenantId = aTenantId;
	}

	public Date begins() {
		return this.begins;
	}

	public Date ends() {
		return this.ends;
	}

	@Override
	public int eventVersion() {
		return this.eventVersion;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}

	public String goals() {
		return this.goals;
	}

	public String name() {
		return this.name;
	}

	public ProductId productId() {
		return this.productId;
	}

	public SprintId sprintId() {
		return this.sprintId;
	}

	public TenantId tenantId() {
		return this.tenantId;
	}
}
