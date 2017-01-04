package com.abigdreamer.saasovation.agilepm.domain.model.product.release;

import java.util.*;

import com.abigdreamer.saasovation.agilepm.domain.model.Entity;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.*;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  计划发布
 * 
 * @author Darkness
 * @date 2014-5-8 下午7:39:50 
 * @version V1.0
 */
public class Release extends Entity {

    private boolean archived;
    private Set<ScheduledBacklogItem> backlogItems;
    private Date begins;
    private String description;
    private Date ends;
    private String name;
    private ProductId productId;
    private ReleaseId releaseId;
    private TenantId tenantId;

    public Release(
            TenantId aTenantId,
            ProductId aProductId,
            ReleaseId aReleaseId,
            String aName,
            String aDescription,
            Date aBegins,
            Date anEnds) {

        this();

        if (anEnds.before(aBegins)) {
            throw new IllegalArgumentException("Release must not end before it begins.");
        }

        this.setBegins(aBegins);
        this.setDescription(aDescription);
        this.setEnds(anEnds);
        this.setName(aName);
        this.setProductId(aProductId);
        this.setReleaseId(aReleaseId);
        this.setTenantId(aTenantId);
    }

    public Set<ScheduledBacklogItem> allScheduledBacklogItems() {
        return Collections.unmodifiableSet(this.backlogItems());
    }

    /**
     * 设置计划发布激活状态
     * @param anArchived
     */
    public void archived(boolean anArchived) {
        this.setArchived(anArchived);

        // TODO: publish event / student assignment
    }

    public Date begins() {
        return this.begins;
    }

    /**
     * 设置描述
     * @param aDescription
     */
    public void describeAs(String aDescription) {
        this.setDescription(aDescription);

        // TODO: publish event / student assignment
    }

    public String description() {
        return this.description;
    }

    public Date ends() {
        return this.ends;
    }

    public boolean isArchived() {
        return this.archived;
    }

    public String name() {
        return this.name;
    }

    public void nowBeginsOn(Date aBegins) {
        this.setBegins(aBegins);

        // TODO: publish event / student assignment
    }

    public void nowEndsOn(Date anEnds) {
        this.setEnds(anEnds);

        // TODO: publish event / student assignment
    }

    public ProductId productId() {
        return this.productId;
    }

    public ReleaseId releaseId() {
        return this.releaseId;
    }

    public void rename(String aName) {
        this.setName(aName);

        // TODO: publish event / student assignment
    }

    public void reorderFrom(BacklogItemId anId, int anOrderOfPriority) {
        for (ScheduledBacklogItem scheduledBacklogItem : this.backlogItems()) {
            scheduledBacklogItem.reorderFrom(anId, anOrderOfPriority);
        }
    }

    public void schedule(BacklogItem aBacklogItem) {
        this.assertArgumentEquals(this.tenantId(), aBacklogItem.tenantId(), "Must have same tenants.");
        this.assertArgumentEquals(this.productId(), aBacklogItem.productId(), "Must have same products.");

        int ordering = this.backlogItems().size() + 1;

        ScheduledBacklogItem scheduledBacklogItem =
                new ScheduledBacklogItem(
                        this.tenantId(),
                        this.releaseId(),
                        aBacklogItem.backlogItemId(),
                        ordering);

        this.backlogItems().add(scheduledBacklogItem);
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    /**
     * 取消待定项的日程安排
     * @param aBacklogItem
     */
    public void unschedule(BacklogItem aBacklogItem) {
        this.assertArgumentEquals(this.tenantId(), aBacklogItem.tenantId(), "Must have same tenants.");
        this.assertArgumentEquals(this.productId(), aBacklogItem.productId(), "Must have same products.");

        ScheduledBacklogItem scheduledBacklogItem =
                new ScheduledBacklogItem(
                        this.tenantId(),
                        this.releaseId(),
                        aBacklogItem.backlogItemId());

        this.backlogItems().remove(scheduledBacklogItem);
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Release typedObject = (Release) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.productId().equals(typedObject.productId()) &&
                this.releaseId().equals(typedObject.releaseId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (84519 * 41)
            + this.tenantId().hashCode()
            + this.productId().hashCode()
            + this.releaseId().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Release [tenantId=" + tenantId + ", productId=" + productId
                + ", releaseId=" + releaseId + ", archived=" + archived
                + ", backlogItems=" + backlogItems + ", begins=" + begins
                + ", description=" + description + ", ends=" + ends
                + ", name=" + name + "]";
    }

    private Release() {
        super();

        this.setBacklogItems(new HashSet<ScheduledBacklogItem>(0));
    }

    private void setArchived(boolean anArchived) {
        this.archived = anArchived;
    }

    private Set<ScheduledBacklogItem> backlogItems() {
        return this.backlogItems;
    }

    private void setBacklogItems(Set<ScheduledBacklogItem> aBacklogItems) {
        this.backlogItems = aBacklogItems;
    }

    private void setBegins(Date aBegins) {
        this.assertArgumentNotNull(aBegins, "The begins must be provided.");

        this.begins = aBegins;
    }

    private void setDescription(String aDescription) {
        this.assertArgumentLength(aDescription, 500, "The description must be 500 characters or less.");

        this.description = aDescription;
    }

    private void setEnds(Date anEnds) {
        this.assertArgumentNotNull(anEnds, "The ends must be provided.");

        this.ends = anEnds;
    }

    private void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "The name must be provided.");
        this.assertArgumentLength(aName, 100, "The name must be 100 characters or less.");

        this.name = aName;
    }

    private void setProductId(ProductId aProductId) {
        this.assertArgumentNotNull(aProductId, "The product id must be provided.");

        this.productId = aProductId;
    }

    private void setReleaseId(ReleaseId aReleaseId) {
        this.assertArgumentNotNull(aReleaseId, "The release id must be provided.");

        this.releaseId = aReleaseId;
    }

    private void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenant id must be provided.");

        this.tenantId = aTenantId;
    }
}
