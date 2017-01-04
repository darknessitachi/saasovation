package com.abigdreamer.saasovation.agilepm.domain.model.team;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;

public class ProductOwnerId extends ValueObject {

	private String id;
	private TenantId tenantId;

	public ProductOwnerId(TenantId aTenantId, String anId) {
		this();

		this.setId(anId);
		this.setTenantId(aTenantId);
	}

	public ProductOwnerId(ProductOwnerId aProductOwnerId) {
		this(aProductOwnerId.tenantId(), aProductOwnerId.id());
	}

	public String id() {
		return this.id;
	}

	public TenantId tenantId() {
		return this.tenantId;
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			ProductOwnerId typedObject = (ProductOwnerId) anObject;
			equalObjects = this.tenantId().equals(typedObject.tenantId()) && this.id().equals(typedObject.id());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(43685 * 83) + this.id().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return "ProductOwnerId [tenantId=" + tenantId + ", id=" + id + "]";
	}

	private ProductOwnerId() {
		super();
	}

	private void setId(String anId) {
		this.assertArgumentNotEmpty(anId, "The id must be provided.");
		this.assertArgumentLength(anId, 36, "The id must be 36 characters or less.");

		this.id = anId;
	}

	private void setTenantId(TenantId aTenantId) {
		this.assertArgumentNotNull(aTenantId, "The tenantId must be provided.");

		this.tenantId = aTenantId;
	}
}
