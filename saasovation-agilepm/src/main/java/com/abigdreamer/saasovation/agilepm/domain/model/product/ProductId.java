package com.abigdreamer.saasovation.agilepm.domain.model.product;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;

public class ProductId extends ValueObject {

	private String id;

	public ProductId(String anId) {
		this();

		this.setId(anId);
	}

	public ProductId(ProductId aProductId) {
		this(aProductId.id());
	}

	public String id() {
		return this.id;
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			ProductId typedObject = (ProductId) anObject;
			equalObjects = this.id().equals(typedObject.id());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(57853 * 31) + this.id().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return "ProductId [id=" + id + "]";
	}

	private ProductId() {
		super();
	}

	private void setId(String anId) {
		this.assertArgumentNotEmpty(anId, "The id must be provided.");
		this.assertArgumentLength(anId, 36, "The id must be 36 characters or less.");

		this.id = anId;
	}
}
