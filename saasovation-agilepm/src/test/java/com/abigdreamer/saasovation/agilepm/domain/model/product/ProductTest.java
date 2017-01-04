package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.process.ProcessId;
import com.abigdreamer.saasovation.agilepm.domain.model.DomainRegistry;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionDescriptor;
import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductBacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductBacklogItemPlanned;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCreated;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductDiscussionInitiated;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductDiscussionRequested;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductReleaseScheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductSprintScheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemType;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.StoryPoints;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.Release;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.Sprint;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwnerId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  产品测试
 * 
 * @author Darkness
 * @date 2014-5-8 下午7:03:35 
 * @version V1.0
 */
public class ProductTest extends ProductCommonTest {

    public ProductTest() {
        super();
    }

    // 测试创建产品
    public void testCreate() throws Exception {
        TenantId tenantId = new TenantId("T12345");

        Product product =
                new Product(
                        tenantId,
                        new ProductId("P12345"),
                        new ProductOwnerId(tenantId, "zdoe"),
                        "My Product",
                        "This is the description of my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        DomainRegistry.productRepository().save(product);

        assertNotNull(product);
        assertEquals("My Product", product.name());
        assertEquals("This is the description of my product.", product.description());
        assertEquals(DiscussionAvailability.NOT_REQUESTED, product.discussion().availability());
        assertNull(product.discussionInitiationId());

        expectedEvents(1);
        expectedEvent(ProductCreated.class);
    }

    // 测试计划待定项
    public void testPlanBacklogItem() throws Exception {
        Product product = this.productForTest();

        BacklogItem backlogItem =
                product.planBacklogItem(
                        new BacklogItemId("B12345"),
                        "Support threaded discussions for Scrum products and backlog items.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        StoryPoints.EIGHT);

        assertNotNull(backlogItem);

        expectedEvents(2);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
    }

	// 测试完成计划的待定项
    public void testPlannedBacklogItem() throws Exception {
        Product product = this.productForTest();

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        expectedEvents(4);
        expectedEvent(ProductCreated.class, 1);
        expectedEvent(ProductBacklogItemPlanned.class, 3);

        product.plannedProductBacklogItem(backlogItem1);
        product.plannedProductBacklogItem(backlogItem2);
        product.plannedProductBacklogItem(backlogItem3);

        for (ProductBacklogItem productBacklogItem : product.allBacklogItems()) {
            if (productBacklogItem.ordering() == 1) {
                assertTrue(productBacklogItem.backlogItemId().id().endsWith("-1"));
            }
            if (productBacklogItem.ordering() == 2) {
                assertTrue(productBacklogItem.backlogItemId().id().endsWith("-2"));
            }
            if (productBacklogItem.ordering() == 3) {
                assertTrue(productBacklogItem.backlogItemId().id().endsWith("-3"));
            }
        }
    }

    // 测试重新排序产品待定项
    public void testReorderFrom() throws Exception {
        Product product = this.productForTest();

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        expectedEvents(4);
        expectedEvent(ProductCreated.class, 1);
        expectedEvent(ProductBacklogItemPlanned.class, 3);

        product.plannedProductBacklogItem(backlogItem1);
        product.plannedProductBacklogItem(backlogItem2);
        product.plannedProductBacklogItem(backlogItem3);

        ProductBacklogItem productBacklogItem1 = null;
        ProductBacklogItem productBacklogItem2 = null;
        ProductBacklogItem productBacklogItem3 = null;

        for (ProductBacklogItem productBacklogItem : product.allBacklogItems()) {
            if (productBacklogItem.ordering() == 1) {
                productBacklogItem1 = productBacklogItem;
            }
            if (productBacklogItem.ordering() == 2) {
                productBacklogItem2 = productBacklogItem;
            }
            if (productBacklogItem.ordering() == 3) {
                productBacklogItem3 = productBacklogItem;
            }
        }

        product.reorderFrom(backlogItem3.backlogItemId(), 1);

        assertEquals(1, productBacklogItem3.ordering());
        assertEquals(2, productBacklogItem1.ordering());
        assertEquals(3, productBacklogItem2.ordering());
    }

    // 测试请求且初始化讨论
    public void testRequestAndInitiateDiscussion() throws Exception {
        Product product = this.productForTest();

        product.requestDiscussion(DiscussionAvailability.REQUESTED);

        assertTrue(product.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.REQUESTED, product.discussion().availability());

        expectedEvents(2);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductDiscussionRequested.class);

        // eventually...
        ProcessId processId = ProcessId.newProcessId();
        product.startDiscussionInitiation(processId.id());

        // eventually...
        product.initiateDiscussion(new DiscussionDescriptor("CollabDiscussion12345"));

        expectedEvents(3);
        expectedEvent(ProductDiscussionInitiated.class);

        assertEquals(processId.id(), product.discussionInitiationId());
        assertFalse(product.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.READY, product.discussion().availability());
    }

    // 测试请求讨论且请求失败
    public void testRequestAndFailedDiscussion() throws Exception {
        Product product = this.productForTest();

        product.requestDiscussion(DiscussionAvailability.REQUESTED);

        assertTrue(product.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.REQUESTED, product.discussion().availability());

        expectedEvents(2);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductDiscussionRequested.class);

        // eventually...
        ProcessId processId = ProcessId.newProcessId();
        product.startDiscussionInitiation(processId.id());

        // eventually...
        product.failDiscussionInitiation();

        expectedEvents(2);

        assertNull(product.discussionInitiationId());
        assertTrue(product.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.FAILED, product.discussion().availability());
    }

    // 测试发布日程
    public void testScheduleRelease() throws Exception {
        Product product = this.productForTest();

        Date begins = new Date();
        Date ends = new Date(begins.getTime() + (86400000L * 30L));

        Release release =
                product.scheduleRelease(
                        new ReleaseId("R-12345"),
                        "Release 1.3",
                        "Enterprise interactive release.",
                        begins,
                        ends);

        assertNotNull(release);
        assertEquals("Release 1.3", release.name());
        assertEquals("Enterprise interactive release.", release.description());

        expectedEvents(2);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductReleaseScheduled.class);
    }

    // 测试计划冲刺日程
    public void testScheduleSprint() throws Exception {
        Product product = this.productForTest();

        Date begins = new Date();
        Date ends = new Date(begins.getTime() + (86400000L * 30L));

        Sprint sprint = product.scheduleSprint(
	                        new SprintId("S12345"),
	                        "Collaboration Integration Sprint",
	                        "Make Scrum project collaboration possible.",
	                        begins,
	                        ends);

        assertNotNull(sprint);
        assertEquals("Collaboration Integration Sprint", sprint.name());
        assertEquals("Make Scrum project collaboration possible.", sprint.goals());

        expectedEvents(2);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductSprintScheduled.class);
    }
}
