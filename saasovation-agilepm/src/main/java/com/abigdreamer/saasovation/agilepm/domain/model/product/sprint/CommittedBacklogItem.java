package com.abigdreamer.saasovation.agilepm.domain.model.product.sprint;

import com.abigdreamer.saasovation.agilepm.domain.model.Entity;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;

/**
 * 提交到冲刺的待定项
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:41:03
 * @version V1.0
 */
public class CommittedBacklogItem extends Entity {

    private BacklogItemId backlogItemId;
    private int ordering;
    private SprintId sprintId;
    private TenantId tenantId;

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public int ordering() {
        return this.ordering;
    }

    public SprintId sprintId() {
        return this.sprintId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            CommittedBacklogItem typedObject = (CommittedBacklogItem) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.sprintId().equals(typedObject.sprintId()) &&
                this.backlogItemId().equals(typedObject.backlogItemId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (282891 * 53)
            + this.tenantId().hashCode()
            + this.sprintId().hashCode()
            + this.backlogItemId().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "CommittedBacklogItem [sprintId=" + sprintId + ", ordering=" + ordering + "]";
    }

    protected CommittedBacklogItem(
            TenantId aTenantId,
            SprintId aSprintId,
            BacklogItemId aBacklogItemId,
            int anOrdering) {

        this();

        this.setBacklogItemId(aBacklogItemId);
        this.setOrdering(anOrdering);
        this.setSprintId(aSprintId);
        this.setTenantId(aTenantId);
    }

    protected CommittedBacklogItem(
            TenantId aTenantId,
            SprintId aSprintId,
            BacklogItemId aBacklogItemId) {

        this(aTenantId, aSprintId, aBacklogItemId, 0);
    }

    private CommittedBacklogItem() {
        super();
    }

    protected void reorderFrom(BacklogItemId anId, int anOrderOfPriority) {
        if (this.backlogItemId().equals(anId)) {
            this.setOrdering(anOrderOfPriority);
        } else if (this.ordering() >= anOrderOfPriority) {
            this.setOrdering(this.ordering() + 1);
        }
    }

    protected void setOrdering(int anOrdering) {
        this.ordering = anOrdering;
    }

    private void setBacklogItemId(BacklogItemId aBacklogItemId) {
        this.assertArgumentNotNull(aBacklogItemId, "The backlog item id must be provided.");

        this.backlogItemId = aBacklogItemId;
    }

    private void setSprintId(SprintId aSprintId) {
        this.assertArgumentNotNull(aSprintId, "The sprint id must be provided.");

        this.sprintId = aSprintId;
    }

    private void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenant id must be provided.");

        this.tenantId = aTenantId;
    }
}
