package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.UUID;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;


public class TaskId extends ValueObject {

	private String id;

	public TaskId() {
		super();

		this.setId(UUID.randomUUID().toString().toUpperCase().substring(0, 8));
	}

	public TaskId(String anId) {
		super();

		this.setId(anId);
	}

	public TaskId(TaskId aTaskId) {
		this(aTaskId.id());
	}

	public String id() {
		return this.id;
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			TaskId typedObject = (TaskId) anObject;
			equalObjects = this.id().equals(typedObject.id());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(88279 * 37) + this.id().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return "TaskId [id=" + id + "]";
	}

	private void setId(String anId) {
		this.assertArgumentNotEmpty(anId, "The id must be provided.");
		this.assertArgumentLength(anId, 8, "The id must be 8 characters or less.");

		this.id = anId;
	}
}
