package com.abigdreamer.saasovation.agilepm.domain.model.product.sprint;

import java.util.*;

import com.abigdreamer.saasovation.agilepm.domain.model.Entity;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.*;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  冲刺[迭代]
 * 
 * @author Darkness
 * @date 2014-5-4 下午12:56:26 
 * @version V1.0
 */
public class Sprint extends Entity {

    private Set<CommittedBacklogItem> backlogItems;
    private Date begins;
    private Date ends;
    private String goals;
    private String name;
    private ProductId productId;
    private String retrospective;
    private SprintId sprintId;
    private TenantId tenantId;

    public Sprint(
            TenantId aTenantId,
            ProductId aProductId,
            SprintId aSprintId,
            String aName,
            String aGoals,
            Date aBegins,
            Date anEnds) {

        this();

        if (anEnds.before(aBegins)) {
            throw new IllegalArgumentException("Sprint must not end before it begins.");
        }

        this.setBegins(aBegins);
        this.setEnds(anEnds);
        this.setGoals(aGoals);
        this.setName(aName);
        this.setProductId(aProductId);
        this.setSprintId(aSprintId);
        this.setTenantId(aTenantId);
    }

    /**
     * 调整冲刺目标
     * @param aGoals
     */
    public void adjustGoals(String aGoals) {
        this.setGoals(aGoals);

        // TODO: publish event / student assignment
    }

    public Set<CommittedBacklogItem> allCommittedBacklogItems() {
        return Collections.unmodifiableSet(this.backlogItems());
    }

    public Date begins() {
        return this.begins;
    }

    /**
     * 捕获可回顾的会议结果
     * @param aRetrospective
     */
    public void captureRetrospectiveMeetingResults(String aRetrospective) {
        this.setRetrospective(aRetrospective);

        // TODO: publish event / student assignment
    }

    public void commit(BacklogItem aBacklogItem) {
        this.assertArgumentEquals(this.tenantId(), aBacklogItem.tenantId(), "Must have same tenants.");
        this.assertArgumentEquals(this.productId(), aBacklogItem.productId(), "Must have same products.");

        int ordering = this.backlogItems().size() + 1;

        CommittedBacklogItem committedBacklogItem =
                new CommittedBacklogItem(
                        this.tenantId(),
                        this.sprintId(),
                        aBacklogItem.backlogItemId(),
                        ordering);

        this.backlogItems().add(committedBacklogItem);
    }

    public Date ends() {
        return this.ends;
    }

    public String goals() {
        return this.goals;
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

    /**
     * 重命名冲刺
     * @param aName
     */
    public void rename(String aName) {
        this.setName(aName);

        // TODO: publish event / student assignment
    }

    /**
     * 重新排序
     * @param anId
     * @param anOrderOfPriority
     */
    public void reorderFrom(BacklogItemId anId, int anOrderOfPriority) {
        for (CommittedBacklogItem committedBacklogItem : this.backlogItems()) {
            committedBacklogItem.reorderFrom(anId, anOrderOfPriority);
        }
    }

    public String retrospective() {
        return this.retrospective;
    }

    public SprintId sprintId() {
        return this.sprintId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public void uncommit(BacklogItem aBacklogItem) {
        CommittedBacklogItem cbi =
                new CommittedBacklogItem(
                        this.tenantId(),
                        this.sprintId(),
                        aBacklogItem.backlogItemId());

        this.backlogItems.remove(cbi);
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Sprint typedObject = (Sprint) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.productId().equals(typedObject.productId()) &&
                this.sprintId().equals(typedObject.sprintId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (11873 * 53)
            + this.tenantId().hashCode()
            + this.productId().hashCode()
            + this.sprintId().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Sprint [tenantId=" + tenantId + ", productId=" + productId
                + ", sprintId=" + sprintId + ", backlogItems="
                + backlogItems + ", begins=" + begins + ", ends=" + ends
                + ", goals=" + goals + ", name=" + name
                + ", retrospective=" + retrospective + "]";
    }

    private Sprint() {
        super();

        this.setBacklogItems(new HashSet<CommittedBacklogItem>(0));
    }

    private Set<CommittedBacklogItem> backlogItems() {
        return this.backlogItems;
    }

    private void setBacklogItems(Set<CommittedBacklogItem> aBacklogItems) {
        this.backlogItems = aBacklogItems;
    }

    private void setBegins(Date aBegins) {
        this.assertArgumentNotNull(aBegins, "The begins must be provided.");

        this.begins = aBegins;
    }

    private void setEnds(Date anEnds) {
        this.assertArgumentNotNull(anEnds, "The ends must be provided.");

        this.ends = anEnds;
    }

    private void setGoals(String aGoals) {
        if (aGoals != null) {
            this.assertArgumentLength(aGoals, 500, "The goals must be 500 characters or less.");
        }

        this.goals = aGoals;
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

    private void setRetrospective(String aRetrospective) {
        if (aRetrospective != null) {
            this.assertArgumentLength(aRetrospective, 5000, "The goals must be 5000 characters or less.");
        }

        this.retrospective = aRetrospective;
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
