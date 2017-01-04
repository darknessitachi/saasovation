package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Date;

import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 产品负责人
 * Product Owner的职责： 
 * 	确定产品的功能。 
 * 	决定发布的日期和发布内容。 
 * 	为产品的profitability of the product (ROI)负责。 
 * 	根据市场价值确定功能优先级。 
 * 	在30天内调整功能和调整功能优先级。 
 * 	接受或拒绝接受开发团队的工作成果。
 * 
 * @author Darkness
 * @date 2014-5-4 下午1:01:06
 * @version V1.0
 */
public class ProductOwner extends Member {

	public ProductOwner(TenantId aTenantId, String aUsername, String aFirstName, String aLastName, String anEmailAddress, Date anInitializedOn) {

		super(aTenantId, aUsername, aFirstName, aLastName, anEmailAddress, anInitializedOn);
	}

	public ProductOwnerId productOwnerId() {
		return new ProductOwnerId(this.tenantId(), this.username());
	}

	@Override
	public boolean equals(Object anObject) {
		boolean equalObjects = false;

		if (anObject != null && this.getClass() == anObject.getClass()) {
			ProductOwner typedObject = (ProductOwner) anObject;
			equalObjects = this.tenantId().equals(typedObject.tenantId()) && this.username().equals(typedObject.username());
		}

		return equalObjects;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = +(71121 * 79) + this.tenantId().hashCode() + this.username().hashCode();

		return hashCodeValue;
	}

	@Override
	public String toString() {
		return "ProductOwner [productOwnerId()=" + productOwnerId() + ", emailAddress()=" + emailAddress() + ", isEnabled()=" + isEnabled() + ", firstName()=" + firstName() + ", lastName()=" + lastName() + ", tenantId()=" + tenantId() + ", username()=" + username() + "]";
	}

	protected ProductOwner() {
		super();
	}
}
