package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.saasovation.agilepm.domain.model.Entity;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionDescriptor;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemStatus;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemType;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.StoryPoints;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.Release;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.Sprint;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwner;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwnerId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  产品
 * 
 * @author Darkness
 * @date 2014-5-4 下午1:00:28 
 * @version V1.0
 */
public class Product extends Entity {

	private TenantId tenantId;
	
	private ProductId productId;
	
	private String name;
	private String description;
	
	private ProductOwnerId productOwnerId;
	
    private Set<ProductBacklogItem> backlogItems;
    
    private ProductDiscussion discussion;
    private String discussionInitiationId;

    public Product(
            TenantId aTenantId,
            ProductId aProductId,
            ProductOwnerId aProductOwnerId,
            String aName,
            String aDescription,
            DiscussionAvailability aDiscussionAvailability) {

        this();

        this.setTenantId(aTenantId); // must precede productOwnerId for compare
        this.setDescription(aDescription);
        this.setDiscussion(ProductDiscussion.fromAvailability(aDiscussionAvailability));
        this.setDiscussionInitiationId(null);
        this.setName(aName);
        this.setProductId(aProductId);
        this.setProductOwnerId(aProductOwnerId);

        DomainEventPublisher
            .instance()
            .publish(new ProductCreated(
                    this.tenantId(),
                    this.productId(),
                    this.productOwnerId(),
                    this.name(),
                    this.description(),
                    this.discussion().availability().isRequested()));
    }

    public Set<ProductBacklogItem> allBacklogItems() {
        return Collections.unmodifiableSet(this.backlogItems());
    }

    public void changeProductOwner(ProductOwner aProductOwner) {
        if (!this.productOwnerId().equals(aProductOwner.productOwnerId())) {
            this.setProductOwnerId(aProductOwner.productOwnerId());

            // TODO: publish event
        }
    }

    public String description() {
        return this.description;
    }

    public ProductDiscussion discussion() {
        return this.discussion;
    }

    public String discussionInitiationId() {
        return this.discussionInitiationId;
    }

    public void failDiscussionInitiation() {
        if (!this.discussion().availability().isReady()) {
            this.setDiscussionInitiationId(null);
            this.setDiscussion(
                    ProductDiscussion
                        .fromAvailability(
                                DiscussionAvailability.FAILED));
        }
    }

    /**
     * 初始化讨论
     * @param aDescriptor
     */
    public void initiateDiscussion(DiscussionDescriptor aDescriptor) {
        if (aDescriptor == null) {
            throw new IllegalArgumentException("The descriptor must not be null.");
        }

        if (this.discussion().availability().isRequested()) {
            this.setDiscussion(this.discussion().nowReady(aDescriptor));

            DomainEventPublisher
                .instance()
                .publish(new ProductDiscussionInitiated(
                        this.tenantId(),
                        this.productId(),
                        this.discussion()));
        }
    }

    public String name() {
        return this.name;
    }

    public BacklogItem planBacklogItem(
            BacklogItemId aNewBacklogItemId,
            String aSummary,
            String aCategory,
            BacklogItemType aType,
            StoryPoints aStoryPoints) {

        BacklogItem backlogItem =
            new BacklogItem(
                    this.tenantId(),
                    this.productId(),
                    aNewBacklogItemId,
                    aSummary,
                    aCategory,
                    aType,
                    BacklogItemStatus.PLANNED,
                    aStoryPoints);

        DomainEventPublisher
            .instance()
            .publish(new ProductBacklogItemPlanned(
                    backlogItem.tenantId(),
                    backlogItem.productId(),
                    backlogItem.backlogItemId(),
                    backlogItem.summary(),
                    backlogItem.category(),
                    backlogItem.type(),
                    backlogItem.storyPoints()));

        return backlogItem;
    }

    public void plannedProductBacklogItem(BacklogItem aBacklogItem) {
        this.assertArgumentEquals(this.tenantId(), aBacklogItem.tenantId(), "The product and backlog item must have same tenant.");
        this.assertArgumentEquals(this.productId(), aBacklogItem.productId(), "The backlog item must belong to product.");

        int ordering = this.backlogItems().size() + 1;

        ProductBacklogItem productBacklogItem =
                new ProductBacklogItem(
                        this.tenantId(),
                        this.productId(),
                        aBacklogItem.backlogItemId(),
                        ordering);

        this.backlogItems().add(productBacklogItem);
    }

    public ProductId productId() {
        return this.productId;
    }

    public ProductOwnerId productOwnerId() {
        return this.productOwnerId;
    }

    public void reorderFrom(BacklogItemId anId, int anOrdering) {
        for (ProductBacklogItem productBacklogItem : this.backlogItems()) {
            productBacklogItem.reorderFrom(anId, anOrdering);
        }
    }

    /**
     * 请求讨论
     * @param aDiscussionAvailability
     */
    public void requestDiscussion(DiscussionAvailability aDiscussionAvailability) {
        if (!this.discussion().availability().isReady()) {
            this.setDiscussion(
                    ProductDiscussion.fromAvailability(
                            aDiscussionAvailability));

            DomainEventPublisher
                .instance()
                .publish(new ProductDiscussionRequested(
                        this.tenantId(),
                        this.productId(),
                        this.productOwnerId(),
                        this.name(),
                        this.description(),
                        this.discussion().availability().isRequested()));
        }
    }

    /**
     * 计划发布日程
     * @param aNewReleaseId
     * @param aName
     * @param aDescription
     * @param aBegins
     * @param anEnds
     * @return
     */
    public Release scheduleRelease(
            ReleaseId aNewReleaseId,
            String aName,
            String aDescription,
            Date aBegins,
            Date anEnds) {

        Release release =
            new Release(
                    this.tenantId(),
                    this.productId(),
                    aNewReleaseId,
                    aName,
                    aDescription,
                    aBegins,
                    anEnds);

        DomainEventPublisher
            .instance()
            .publish(new ProductReleaseScheduled(
                    release.tenantId(),
                    release.productId(),
                    release.releaseId(),
                    release.name(),
                    release.description(),
                    release.begins(),
                    release.ends()));

        return release;
    }

    /**
     * 计划冲刺日程
     * @param aNewSprintId
     * @param aName
     * @param aGoals
     * @param aBegins
     * @param anEnds
     * @return
     */
    public Sprint scheduleSprint(
            SprintId aNewSprintId,
            String aName,
            String aGoals,
            Date aBegins,
            Date anEnds) {

        Sprint sprint =
            new Sprint(
                    this.tenantId(),
                    this.productId(),
                    aNewSprintId,
                    aName,
                    aGoals,
                    aBegins,
                    anEnds);

        DomainEventPublisher
            .instance()
            .publish(new ProductSprintScheduled(
                    sprint.tenantId(),
                    sprint.productId(),
                    sprint.sprintId(),
                    sprint.name(),
                    sprint.goals(),
                    sprint.begins(),
                    sprint.ends()));

        return sprint;
    }

    /**
     * 开始讨论初始化
     * @param aDiscussionInitiationId
     */
    public void startDiscussionInitiation(String aDiscussionInitiationId) {
        if (!this.discussion().availability().isReady()) {
            this.setDiscussionInitiationId(aDiscussionInitiationId);
        }
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Product typedObject = (Product) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.productId().equals(typedObject.productId());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (2335 * 3)
            + this.tenantId().hashCode()
            + this.productId().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Product [tenantId=" + tenantId + ", productId=" + productId
                + ", backlogItems=" + backlogItems + ", description="
                + description + ", discussion=" + discussion
                + ", discussionInitiationId=" + discussionInitiationId
                + ", name=" + name + ", productOwnerId=" + productOwnerId + "]";
    }

    private Product() {
        super();

        this.setBacklogItems(new HashSet<ProductBacklogItem>(0));
    }

    private Set<ProductBacklogItem> backlogItems() {
        return this.backlogItems;
    }

    private void setBacklogItems(Set<ProductBacklogItem> backlogItems) {
        this.backlogItems = backlogItems;
    }

    private void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "The description must be provided.");
        this.assertArgumentLength(aDescription, 500, "Description must be 500 characters or less.");

        this.description = aDescription;
    }

    private void setDiscussion(ProductDiscussion aDiscussion) {
        this.assertArgumentNotNull(aDiscussion, "The discussion is required even if it is unused.");

        this.discussion = aDiscussion;
    }

    private void setDiscussionInitiationId(String aDiscussionInitiationId) {
        if (aDiscussionInitiationId != null) {
            this.assertArgumentLength(
                    aDiscussionInitiationId,
                    100,
                    "Discussion initiation identity must be 100 characters or less.");
        }

        this.discussionInitiationId = aDiscussionInitiationId;
    }

    private void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "The name must be provided.");
        this.assertArgumentLength(aName, 100, "The name must be 100 characters or less.");

        this.name = aName;
    }

    private void setProductId(ProductId aProductId) {
        this.assertArgumentNotNull(aProductId, "The productId must be provided.");

        this.productId = aProductId;
    }

    private void setProductOwnerId(ProductOwnerId aProductOwnerId) {
        this.assertArgumentNotNull(aProductOwnerId, "The productOwnerId must be provided.");
        this.assertArgumentEquals(this.tenantId(), aProductOwnerId.tenantId(), "The productOwner must have the same tenant.");

        this.productOwnerId = aProductOwnerId;
    }

    private void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId must be provided.");

        this.tenantId = aTenantId;
    }
}
