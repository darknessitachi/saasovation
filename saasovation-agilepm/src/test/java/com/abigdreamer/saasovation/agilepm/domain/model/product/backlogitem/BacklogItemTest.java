package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import com.abigdreamer.infinity.ddd.domain.model.process.ProcessId;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionDescriptor;
import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductBacklogItemPlanned;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCommonTest;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCreated;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductReleaseScheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductSprintScheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemCategoryChanged;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemCommitted;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemDiscussionInitiated;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemDiscussionRequested;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemScheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemStatusChanged;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemStoryPointsAssigned;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemStoryTold;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemSummarized;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemType;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemTypeChanged;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemUncommitted;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemUnscheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BusinessPriority;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BusinessPriorityAssigned;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BusinessPriorityRatings;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.StoryPoints;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.Task;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskDefined;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskDescribed;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskHoursRemainingEstimated;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskRemoved;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskRenamed;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskStatus;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskStatusChanged;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.TaskVolunteerAssigned;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.Release;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.Sprint;



/**
 * 待办项测试
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:19:13
 * @version V1.0
 */
public class BacklogItemTest extends ProductCommonTest {

    public BacklogItemTest() {
        super();
    }

    // 测试还有任务未完成
    public void testAnyTaskHoursRemaining() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        assertFalse(backlogItem.anyTaskHoursRemaining());

        Task task1 = this.taskForTest(backlogItem, 1);
        int taskHours1 = task1.hoursRemaining();

        Task task2 = this.taskForTest(backlogItem, 2);
        int taskHours2 = task2.hoursRemaining();

        Task task3 = this.taskForTest(backlogItem, 3);
        int taskHours3 = task3.hoursRemaining();

        // 测试还有任务未完成
        assertTrue(backlogItem.anyTaskHoursRemaining());
        
        // 测试待办项的总剩余时间为各个任务的剩余时间之和
        assertEquals(taskHours1 + taskHours2 + taskHours3, backlogItem.totalTaskHoursRemaining());

        // 移除一个任务
        backlogItem.removeTask(task1.taskId());
        
        // 测试还有任务未完成
        assertTrue(backlogItem.anyTaskHoursRemaining());
        
        // 测试待办项的总剩余时间为各个任务的剩余时间之和
        assertEquals(taskHours2 + taskHours3, backlogItem.totalTaskHoursRemaining());

        // 再移除一个任务
        backlogItem.removeTask(task2.taskId());
        assertTrue(backlogItem.anyTaskHoursRemaining());
        assertEquals(taskHours3, backlogItem.totalTaskHoursRemaining());

        // 移除剩下的最后一个任务
        backlogItem.removeTask(task3.taskId());
        
        // 测试待办项没有任务未完成
        assertFalse(backlogItem.anyTaskHoursRemaining());
        // 测试待办项总剩余时间为0
        assertEquals(0, backlogItem.totalTaskHoursRemaining());
    }

    // 测试指定业务价值
    public void testAssignBusinessPriority() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        BusinessPriority businessPriority =
                new BusinessPriority(new BusinessPriorityRatings(2, 4, 1, 1));

        backlogItem.assignBusinessPriority(businessPriority);

        expectedEvents(3);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(BusinessPriorityAssigned.class);
    }

    // 测试分配任务点
    public void testAssignStoryPoints() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        backlogItem.assignStoryPoints(StoryPoints.TWENTY);

        assertEquals(StoryPoints.TWENTY, backlogItem.storyPoints());

        expectedEvents(3);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(BacklogItemStoryPointsAssigned.class);
    }

    // 测试分配任务志愿者
    public void testAssignTaskVolunteer() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        Task task = this.taskForTest(backlogItem, 1);

        backlogItem.assignTaskVolunteer(task.taskId(), this.teamMemberForTest());

        expectedEvents(4);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(TaskDefined.class);
        expectedEvent(TaskVolunteerAssigned.class);
    }

    // 测试改变待办项分类
    public void testChangeCategory() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        assertFalse("User Interface".equals(backlogItem.category()));

        backlogItem.changeCategory("User Interface");

        assertEquals("User Interface", backlogItem.category());

        expectedEvents(3);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(BacklogItemCategoryChanged.class);
    }

    // 测试改变任务状态
    public void testChangeTaskStatus() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        Task task = this.taskForTest(backlogItem, 1);

        assertEquals(TaskStatus.NOT_STARTED, task.status());

        backlogItem.changeTaskStatus(task.taskId(), TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.IN_PROGRESS, task.status());

        expectedEvents(4);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(TaskDefined.class);
        expectedEvent(TaskStatusChanged.class);
    }

    // 测试改变待办项类别
    public void testChangeType() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        assertTrue(backlogItem.type().equals(BacklogItemType.FEATURE));
        assertFalse(backlogItem.type().equals(BacklogItemType.ENHANCEMENT));

        backlogItem.changeType(BacklogItemType.ENHANCEMENT);

        assertFalse(backlogItem.type().equals(BacklogItemType.FEATURE));
        assertTrue(backlogItem.type().equals(BacklogItemType.ENHANCEMENT));

        expectedEvents(3);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(BacklogItemTypeChanged.class);
    }

    // 测试任务待办项提交冲刺
    public void testCommitTo() {
        Product product = this.productForTest();

        BacklogItem backlogItem = this.backlogItemForTest(product);

        Sprint sprint = this.sprintForTest(product);

        try {
            backlogItem.commitTo(sprint);

            fail("Must be scheduled for release before committing to sprint.");

        } catch (IllegalStateException e) {
            // good
        }

        Release release = this.releaseForTest(product);

        backlogItem.scheduleFor(release);

        // later...
        backlogItem.commitTo(sprint);

        expectedEvents(6);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(ProductSprintScheduled.class);
        expectedEvent(ProductReleaseScheduled.class);
        expectedEvent(BacklogItemScheduled.class);
        expectedEvent(BacklogItemCommitted.class);
    }

    // 测试定义任务
    public void testDefineTask() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        backlogItem.defineTask(
                this.teamMemberForTest(),
                "Model the Discussion",
                "Collaboration discussions must manage transitioning state.",
                1);

        assertFalse(backlogItem.allTasks().isEmpty());
        assertEquals(1, backlogItem.allTasks().size());

        expectedEvents(3);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(TaskDefined.class);
    }

    // 测试描述任务
    public void testDescribeTask() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        Task task = this.taskForTest(backlogItem, 1);

        assertFalse("New and improved description.".equals(task.description()));

        backlogItem.describeTask(task.taskId(), "New and improved description.");

        assertEquals("New and improved description.", task.description());

        expectedEvents(4);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(TaskDefined.class);
        expectedEvent(TaskDescribed.class);
    }

    // 测试申请且初始化讨论
    public void testRequestAndInitiateDiscussion() throws Exception {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        backlogItem.requestDiscussion(DiscussionAvailability.REQUESTED);

        assertTrue(backlogItem.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.REQUESTED, backlogItem.discussion().availability());

        expectedEvents(3);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(BacklogItemDiscussionRequested.class);

        // eventually...
        ProcessId processId = ProcessId.newProcessId();
        backlogItem.startDiscussionInitiation(processId.id());

        // eventually...
        backlogItem.initiateDiscussion(new DiscussionDescriptor("CollabDiscussion45678"));

        expectedEvents(4);
        expectedEvent(BacklogItemDiscussionInitiated.class);

        assertEquals(processId.id(), backlogItem.discussionInitiationId());
        assertFalse(backlogItem.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.READY, backlogItem.discussion().availability());
    }

    // 测试申请&讨论失败
    public void testRequestAndFailedDiscussion() throws Exception {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        backlogItem.requestDiscussion(DiscussionAvailability.REQUESTED);

        assertTrue(backlogItem.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.REQUESTED, backlogItem.discussion().availability());

        expectedEvents(3);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(BacklogItemDiscussionRequested.class);

        // eventually...
        ProcessId processId = ProcessId.newProcessId();
        backlogItem.startDiscussionInitiation(processId.id());

        // eventually...
        backlogItem.failDiscussionInitiation();

        expectedEvents(3);

        assertNull(backlogItem.discussionInitiationId());
        assertTrue(backlogItem.discussion().descriptor().isUndefined());
        assertEquals(DiscussionAvailability.FAILED, backlogItem.discussion().availability());
    }

    // 测试预估任务剩余时间
    public void testEstimateTaskHoursRemaining() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        Task task = this.taskForTest(backlogItem, 1);

        assertTrue(backlogItem.isPlanned());

        assertEquals(TaskStatus.NOT_STARTED, task.status());

        backlogItem.estimateTaskHoursRemaining(task.taskId(), task.hoursRemaining() / 2);

        assertEquals(TaskStatus.IN_PROGRESS, task.status());

        assertEquals(1, task.allEstimationLogEntries().size());
        assertEquals(task.hoursRemaining(), task.allEstimationLogEntries().get(0).hoursRemaining());

        expectedEvents(5);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(TaskDefined.class);
        expectedEvent(TaskHoursRemainingEstimated.class);
        expectedEvent(TaskStatusChanged.class);

        // later...
        backlogItem.estimateTaskHoursRemaining(task.taskId(), 0);

        assertEquals(TaskStatus.DONE, task.status());

        // same day re-estimation means that existing log is changed
        assertEquals(1, task.allEstimationLogEntries().size());
        assertEquals(0, task.allEstimationLogEntries().get(0).hoursRemaining());

        expectedEvents(8);
        expectedEvent(TaskHoursRemainingEstimated.class, 2);
        expectedEvent(TaskStatusChanged.class, 2);
        expectedEvent(BacklogItemStatusChanged.class);

        assertTrue(backlogItem.isDone());
    }

    // 测试待办项标记删除
    public void testMarkAsRemoved() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        assertTrue(backlogItem.isPlanned());
        assertFalse(backlogItem.isRemoved());

        backlogItem.markAsRemoved();

        assertFalse(backlogItem.isPlanned());
        assertTrue(backlogItem.isRemoved());
    }

    // 测试删除任务
    public void testRemoveTask() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        assertFalse(backlogItem.anyTaskHoursRemaining());

        Task task1 = this.taskForTest(backlogItem, 1);
        Task task2 = this.taskForTest(backlogItem, 2);
        Task task3 = this.taskForTest(backlogItem, 3);

        assertEquals(3, backlogItem.allTasks().size());

        backlogItem.removeTask(task1.taskId());
        assertTrue(backlogItem.anyTaskHoursRemaining());

        backlogItem.removeTask(task2.taskId());
        assertTrue(backlogItem.anyTaskHoursRemaining());

        backlogItem.removeTask(task3.taskId());
        assertFalse(backlogItem.anyTaskHoursRemaining());

        assertEquals(0, backlogItem.allTasks().size());

        expectedEvent(TaskRemoved.class, 3);
    }

    // 测试重命名任务
    public void testRenameTask() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        Task task = this.taskForTest(backlogItem, 1);

        assertFalse(task.name().equals("New and Improved Task Name"));

        backlogItem.renameTask(task.taskId(), "New and Improved Task Name");

        assertEquals("New and Improved Task Name", task.name());

        expectedEvent(TaskRenamed.class);
    }

    // 测试发布计划日程
    public void testScheduleFor() {
        Product product = this.productForTest();

        BacklogItem backlogItem = this.backlogItemForTest(product);

        Release release = this.releaseForTest(product);

        backlogItem.scheduleFor(release);

        expectedEvents(4);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(ProductReleaseScheduled.class);
        expectedEvent(BacklogItemScheduled.class);
    }

    // 测试设置待办项摘要
    public void testSummarize() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        assertFalse(backlogItem.summary().equals("New and Improved Summary"));

        backlogItem.summarize("New and Improved Summary");

        assertEquals("New and Improved Summary", backlogItem.summary());

        expectedEvent(BacklogItemSummarized.class);
    }

    // 测试设置用户故事
    public void testTellStory() {
        BacklogItem backlogItem = this.backlogItemForTest(this.productForTest());

        assertFalse(backlogItem.summary().equals("New and Improved Story"));

        backlogItem.tellStory("New and Improved Story");

        assertEquals("New and Improved Story", backlogItem.story());

        expectedEvent(BacklogItemStoryTold.class);
    }

    // 测试取消提交到冲刺
    public void testUncommitFromSprint() {
        Product product = this.productForTest();

        BacklogItem backlogItem = this.backlogItemForTest(product);

        Release release = this.releaseForTest(product);
        backlogItem.scheduleFor(release);

        Sprint sprint = this.sprintForTest(product);
        backlogItem.commitTo(sprint);

        // later...
        backlogItem.uncommitFromSprint();

        expectedEvents(7);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(ProductSprintScheduled.class);
        expectedEvent(ProductReleaseScheduled.class);
        expectedEvent(BacklogItemScheduled.class);
        expectedEvent(BacklogItemCommitted.class);
        expectedEvent(BacklogItemUncommitted.class);
    }

    // 测试取消发布计划日程
    public void testUnscheduleFromRelease() {
        Product product = this.productForTest();

        BacklogItem backlogItem = this.backlogItemForTest(product);

        Release release = this.releaseForTest(product);
        backlogItem.scheduleFor(release);

        // later...
        backlogItem.unscheduleFromRelease();

        expectedEvents(5);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(ProductReleaseScheduled.class);
        expectedEvent(BacklogItemScheduled.class);
        expectedEvent(BacklogItemUnscheduled.class);
    }

    // 测试取消计划发布失败，在冲刺中的待定项不能取消计划发布
    public void testFailUnscheduleFromRelease() {
        Product product = this.productForTest();

        BacklogItem backlogItem = this.backlogItemForTest(product);

        Release release = this.releaseForTest(product);
        backlogItem.scheduleFor(release);

        Sprint sprint = this.sprintForTest(product);
        backlogItem.commitTo(sprint);

        try {
            // later...
            backlogItem.unscheduleFromRelease();

            fail("The backlog item must first be uncommitted from the sprint.");

        } catch (Exception e) {
            // good
        }

        expectedEvents(6);
        expectedEvent(ProductCreated.class);
        expectedEvent(ProductBacklogItemPlanned.class);
        expectedEvent(ProductReleaseScheduled.class);
        expectedEvent(ProductSprintScheduled.class);
        expectedEvent(BacklogItemScheduled.class);
        expectedEvent(BacklogItemCommitted.class);
    }
}
