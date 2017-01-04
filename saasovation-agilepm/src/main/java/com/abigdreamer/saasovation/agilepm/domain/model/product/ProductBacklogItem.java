package com.abigdreamer.saasovation.agilepm.domain.model.product;

import com.abigdreamer.saasovation.agilepm.domain.model.Entity;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;

/**
 *  产品待定项
 * 根据商业价值排好序的客户需求列表
 * 一个最终会交付给客户的产品特性列表，它们根据商业价值来排列优先级。 
 * @author Darkness
 * @date 2014-5-4 下午1:04:02 
 * @version V1.0
 */
public class ProductBacklogItem extends Entity {

    private BacklogItemId backlogItemId;
    private int ordering;
    private ProductId productId;
    private TenantId tenantId;

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public int ordering() {
        return this.ordering;
    }

    public ProductId productId() {
        return this.productId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            ProductBacklogItem typedObject = (ProductBacklogItem) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.productId().equals(typedObject.productId()) &&
                this.backlogItemId().equals(typedObject.backlogItemId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (15389 * 97)
            + this.tenantId().hashCode()
            + this.productId().hashCode()
            + this.backlogItemId().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "ProductBacklogItem [tenantId=" + tenantId
                + ", productId=" + productId
                + ", backlogItemId=" + backlogItemId
                + ", ordering=" + ordering + "]";
    }

    protected ProductBacklogItem(
            TenantId aTenantId,
            ProductId aProductId,
            BacklogItemId aBacklogItemId,
            int anOrdering) {

        this();

        this.setBacklogItemId(aBacklogItemId);
        this.setOrdering(anOrdering);
        this.setProductId(aProductId);
        this.setTenantId(aTenantId);
    }

    protected ProductBacklogItem() {
        super();
    }

    protected void reorderFrom(BacklogItemId anId, int anOrdering) {
        if (this.backlogItemId().equals(anId)) {
            this.setOrdering(anOrdering);
        } else if (this.ordering() >= anOrdering) {
            this.setOrdering(this.ordering() + 1);
        }
    }

    protected void setBacklogItemId(BacklogItemId aBacklogItemId) {
        this.assertArgumentNotNull(aBacklogItemId, "The backlog item id must be provided.");

        this.backlogItemId = aBacklogItemId;
    }

    protected void setOrdering(int anOrdering) {
        this.ordering = anOrdering;
    }

    protected void setProductId(ProductId aProductId) {
        this.assertArgumentNotNull(aProductId, "The product id must be provided.");

        this.productId = aProductId;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenant id must be provided.");

        this.tenantId = aTenantId;
    }
}
