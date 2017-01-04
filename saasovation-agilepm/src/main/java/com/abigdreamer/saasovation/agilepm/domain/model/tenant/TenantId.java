package com.abigdreamer.saasovation.agilepm.domain.model.tenant;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;

/**
 *  租赁标识
 * 
 * @author Darkness
 * @date 2014-5-6 下午4:28:17 
 * @version V1.0
 */
public class TenantId extends ValueObject {

	private String id;

	public TenantId(String anId) {
		this();

		this.setId(anId);
	}

	public TenantId(TenantId aTenantId) {
		this(aTenantId.id());
	}

	public String id() {
		return this.id;
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			TenantId typedObject = (TenantId) anObject;
			equalObjects = this.id().equals(typedObject.id());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(2785 * 5) + this.id().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return "TenantId [id=" + id + "]";
	}

	protected TenantId() {
		super();
	}

	private void setId(String anId) {
		this.assertArgumentNotEmpty(anId, "The tenant identity is required.");
		this.assertArgumentLength(anId, 36, "The tenant identity must be 36 characters or less.");

		this.id = anId;
	}
}
