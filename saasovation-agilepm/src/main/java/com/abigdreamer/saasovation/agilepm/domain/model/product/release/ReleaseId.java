package com.abigdreamer.saasovation.agilepm.domain.model.product.release;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;

/**
 * 计划发布Id
 * 
 * @author Darkness
 * @date 2014-5-29 下午3:35:19
 * @version V1.0
 */
public class ReleaseId extends ValueObject {

	private String id;

	public ReleaseId(String anId) {
		super();

		this.setId(anId);
	}

	public ReleaseId(ReleaseId aReleaseId) {
		this(aReleaseId.id());
	}

	public String id() {
		return this.id;
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			ReleaseId typedObject = (ReleaseId) anObject;
			equalObjects = this.id().equals(typedObject.id());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(38313 * 43) + this.id().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return "ReleaseId [id=" + id + "]";
	}

	protected ReleaseId() {
		super();
	}

	private void setId(String anId) {
		this.assertArgumentNotEmpty(anId, "The id must be provided.");
		this.assertArgumentLength(anId, 36, "The id must be 36 characters or less.");

		this.id = anId;
	}
}
