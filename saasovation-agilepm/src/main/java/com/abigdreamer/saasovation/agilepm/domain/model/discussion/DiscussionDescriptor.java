package com.abigdreamer.saasovation.agilepm.domain.model.discussion;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;

/**
 * 讨论描述
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:26:47
 * @version V1.0
 */
public class DiscussionDescriptor extends ValueObject {

	public static final String UNDEFINED_ID = "UNDEFINED";

	private String id;

	public DiscussionDescriptor(String anId) {
		this();

		this.setId(anId);
	}

	public DiscussionDescriptor(DiscussionDescriptor aDiscussionDescriptor) {
		this(aDiscussionDescriptor.id());
	}

	public String id() {
		return this.id;
	}

	public boolean isUndefined() {
		return this.id().equals(UNDEFINED_ID);
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			DiscussionDescriptor typedObject = (DiscussionDescriptor) anObject;
			equalObjects = this.id().equals(typedObject.id());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(72881 * 101) + this.id().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return "DiscussionDescriptor [id=" + id + "]";
	}

	private DiscussionDescriptor() {
		super();
	}

	private void setId(String anId) {
		this.assertArgumentNotEmpty(anId, "The discussion identity must be provided.");
		this.assertArgumentLength(anId, 36, "The discussion identity must be 36 characters or less.");

		this.id = anId;
	}
}
