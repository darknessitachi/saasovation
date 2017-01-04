package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import com.abigdreamer.saasovation.agilepm.domain.model.DomainTest;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemType;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.StoryPoints;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.Task;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskDefined;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.Release;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.Sprint;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwnerId;
import com.abigdreamer.saasovation.agilepm.domain.model.team.TeamMember;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;



/**
 * 产品测试基类
 * 
 * @author Darkness
 * @date 2014-5-29 下午3:20:56
 * @version V1.0
 */
public abstract class ProductCommonTest extends DomainTest {

    private TaskId taskId;

    public ProductCommonTest() {
        super();
    }

    protected BacklogItem backlogItemForTest(Product aProduct) {
        return this.backlogItem1ForTest(aProduct);
    }

    protected BacklogItem backlogItem1ForTest(Product aProduct) {
        BacklogItem backlogItem =
                aProduct.planBacklogItem(
                        new BacklogItemId("B12345-1"),
                        "Support threaded discussions for Scrum products.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        StoryPoints.EIGHT);

        return backlogItem;
    }

    protected BacklogItem backlogItem2ForTest(Product aProduct) {
        BacklogItem backlogItem =
                aProduct.planBacklogItem(
                        new BacklogItemId("B12345-2"),
                        "Support threaded discussions for Scrum backlog items.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        StoryPoints.EIGHT);

        return backlogItem;
    }

    protected BacklogItem backlogItem3ForTest(Product aProduct) {
        BacklogItem backlogItem =
                aProduct.planBacklogItem(
                        new BacklogItemId("B12345-3"),
                        "Support threaded discussions for Scrum tasks.",
                        "Domain Model",
                        BacklogItemType.FEATURE,
                        StoryPoints.EIGHT);

        return backlogItem;
    }

    protected Product productForTest() {
        TenantId tenantId = new TenantId("T12345");

        Product product =
            new Product(
                    tenantId,
                    new ProductId("P12345"),
                    new ProductOwnerId(tenantId, "zdoe"),
                    "My Product",
                    "This is the description of my product.",
                    DiscussionAvailability.NOT_REQUESTED);

        return product;
    }

    protected Release releaseForTest(Product aProduct) {
        Date now = new Date();

        Release release =
                aProduct.scheduleRelease(
                        new ReleaseId("R12345"),
                        "Release 1.3",
                        "Enterprise interactive release.",
                        new Date(),
                        new Date(now.getTime() + (86400000L * 30L)));

        return release;
    }

    protected Sprint sprintForTest(Product aProduct) {
        Date now = new Date();

        Sprint sprint =
                aProduct.scheduleSprint(
                        new SprintId("S12345"),
                        "Collaboration Integration Sprint",
                        "Make Scrum project collaboration possible.",
                        new Date(),
                        new Date(now.getTime() + (86400000L * 15L)));

        return sprint;
    }

    // 添加任务到待办项
    protected Task taskForTest(BacklogItem aBacklogItem, int anOption) {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<TaskDefined>() {
            @Override
            public void handleEvent(TaskDefined aDomainEvent) {
                taskId = aDomainEvent.taskId();
            }

            @Override
            public Class<TaskDefined> subscribedToEventType() {
                return TaskDefined.class;
            }
        });

        aBacklogItem.defineTask(
                this.teamMemberOfOption(anOption),
                this.taskNameOfOption(anOption),
                this.taskDescriptionOfOption(anOption),
                this.taskHoursRemainingOfOption(anOption));

        assertNotNull(taskId);

        return aBacklogItem.task(taskId);
    }

    protected String taskDescriptionOfOption(int anOption) {
        String[] tasktaskDescriptionss =
                new String[] {
                    "Task description 1",
                    "Task description 2",
                    "Task description 3" };

        return tasktaskDescriptionss[anOption - 1];
    }

    protected int taskHoursRemainingOfOption(int anOption) {
        int[] taskHoursRemaining =
                new int[] {
                    4,
                    8,
                    10 };

        return taskHoursRemaining[anOption - 1];
    }

    protected String taskNameOfOption(int anOption) {
        String[] taskNames =
                new String[] {
                    "Task 1",
                    "Task 2",
                    "Task 3" };

        return taskNames[anOption - 1];
    }

    protected TeamMember teamMemberOfOption(int anOption) {
        TeamMember[] teamMembers =
                new TeamMember[] {
                    teamMemberForTest1(),
                    teamMemberForTest2(),
                    teamMemberForTest3() };

        return teamMembers[anOption - 1];
    }

    protected TeamMember teamMemberForTest() {
        return this.teamMemberForTest1();
    }

    protected TeamMember teamMemberForTest1() {
        TeamMember teamMember =
                new TeamMember(
                        new TenantId("T12345"),
                        "bill",
                        "Bill",
                        "Smith",
                        "bill@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        return teamMember;
    }

    protected TeamMember teamMemberForTest2() {
        TeamMember teamMember =
                new TeamMember(
                        new TenantId("T12345"),
                        "zoe",
                        "Zoe",
                        "Doe",
                        "zoe@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        return teamMember;
    }

    protected TeamMember teamMemberForTest3() {
        TeamMember teamMember =
                new TeamMember(
                        new TenantId("T12345"),
                        "jdoe",
                        "John",
                        "Doe",
                        "jdoe@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        return teamMember;
    }
}
