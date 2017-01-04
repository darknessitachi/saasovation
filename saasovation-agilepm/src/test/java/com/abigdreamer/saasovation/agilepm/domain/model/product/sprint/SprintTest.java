package com.abigdreamer.saasovation.agilepm.domain.model.product.sprint;

import java.util.Date;


import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductBacklogItemPlanned;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCommonTest;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCreated;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductSprintScheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.CommittedBacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.Sprint;
import com.abigdreamer.saasovation.agilepm.domain.model.product.sprint.SprintId;


/**
 * 冲刺测试
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:19:12
 * @version V1.0
 */
public class SprintTest extends ProductCommonTest {

    public SprintTest() {
        super();
    }

    // 测试计划冲刺
    public void testScheduleSprint() throws Exception {
        Product product = this.productForTest();

        Date begins = new Date();
        Date ends = new Date(begins.getTime() + (86400000L * 30L));

        Sprint sprint =
                product.scheduleSprint(
                        new SprintId("S-12345"),
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

    // 测试捕获可回顾的会议结果
    public void testCaptureRetrospectiveMeetingResults() throws Exception {
        Sprint sprint = this.sprintForTest(this.productForTest());

        assertNull(sprint.retrospective());

        sprint.captureRetrospectiveMeetingResults("We learned these five things: ...");

        assertNotNull(sprint.retrospective());
        assertEquals("We learned these five things: ...", sprint.retrospective());
    }

    // 测试调整冲刺目标
    public void testDescribeAs() throws Exception {
        Sprint sprint = this.sprintForTest(this.productForTest());

        String adjustedGoals = "Make Scrum product and backlog item collaboration possible.";

        assertFalse(adjustedGoals.equals(sprint.goals()));

        sprint.adjustGoals(adjustedGoals);

        assertEquals(adjustedGoals, sprint.goals());
    }

    // 测试冲刺开始时间
    public void testNowBeginsOn() throws Exception {
        Sprint sprint = this.sprintForTest(this.productForTest());

        Date date = new Date(new Date().getTime() + (86400000L * 2L));

        assertFalse(date.equals(sprint.begins()));

        sprint.nowBeginsOn(date);

        assertEquals(date, sprint.begins());
    }

    // 测试冲刺结束时间
    public void testNowEndsOn() throws Exception {
        Sprint sprint = this.sprintForTest(this.productForTest());

        Date date = new Date(new Date().getTime() + (86400000L * 10L));

        assertFalse(date.equals(sprint.ends()));

        sprint.nowEndsOn(date);

        assertEquals(date, sprint.ends());
    }

    // 测试重命名冲刺
    public void testRename() throws Exception {
        Sprint sprint = this.sprintForTest(this.productForTest());

        String changedName = "New Sprint Name";

        assertFalse(changedName.equals(sprint.name()));

        sprint.rename(changedName);

        assertEquals(changedName, sprint.name());
    }

    // 测试重新排序冲刺中的待定项
    public void testReorderFrom() throws Exception {
        Product product = this.productForTest();
        Sprint sprint = this.sprintForTest(product);

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        expectedEvents(5);
        expectedEvent(ProductCreated.class, 1);
        expectedEvent(ProductSprintScheduled.class, 1);
        expectedEvent(ProductBacklogItemPlanned.class, 3);

        sprint.commit(backlogItem1);
        sprint.commit(backlogItem2);
        sprint.commit(backlogItem3);

        CommittedBacklogItem scheduledBacklogItem1 = null;
        CommittedBacklogItem scheduledBacklogItem2 = null;
        CommittedBacklogItem scheduledBacklogItem3 = null;

        for (CommittedBacklogItem scheduledBacklogItem : sprint.allCommittedBacklogItems()) {
            if (scheduledBacklogItem.ordering() == 1) {
                scheduledBacklogItem1 = scheduledBacklogItem;
            }
            if (scheduledBacklogItem.ordering() == 2) {
                scheduledBacklogItem2 = scheduledBacklogItem;
            }
            if (scheduledBacklogItem.ordering() == 3) {
                scheduledBacklogItem3 = scheduledBacklogItem;
            }
        }

        sprint.reorderFrom(backlogItem3.backlogItemId(), 1);

        assertEquals(1, scheduledBacklogItem3.ordering());
        assertEquals(2, scheduledBacklogItem1.ordering());
        assertEquals(3, scheduledBacklogItem2.ordering());
    }

    // 测试待定项冲刺计划日程
    public void testSchedule() throws Exception {
        Product product = this.productForTest();
        Sprint sprint = this.sprintForTest(product);

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        expectedEvents(5);
        expectedEvent(ProductCreated.class, 1);
        expectedEvent(ProductSprintScheduled.class, 1);
        expectedEvent(ProductBacklogItemPlanned.class, 3);

        sprint.commit(backlogItem1);
        sprint.commit(backlogItem2);
        sprint.commit(backlogItem3);

        for (CommittedBacklogItem scheduledBacklogItem : sprint.allCommittedBacklogItems()) {
            if (scheduledBacklogItem.ordering() == 1) {
                assertTrue(scheduledBacklogItem.backlogItemId().id().endsWith("-1"));
            }
            if (scheduledBacklogItem.ordering() == 2) {
                assertTrue(scheduledBacklogItem.backlogItemId().id().endsWith("-2"));
            }
            if (scheduledBacklogItem.ordering() == 3) {
                assertTrue(scheduledBacklogItem.backlogItemId().id().endsWith("-3"));
            }
        }
    }

    // 测试取消待定项冲刺计划
    public void testUnschedule() throws Exception {
        Product product = this.productForTest();
        Sprint sprint = this.sprintForTest(product);

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        sprint.commit(backlogItem1);
        sprint.commit(backlogItem2);
        sprint.commit(backlogItem3);

        assertEquals(3, sprint.allCommittedBacklogItems().size());

        sprint.uncommit(backlogItem2);

        assertEquals(2, sprint.allCommittedBacklogItems().size());

        for (CommittedBacklogItem scheduledBacklogItem : sprint.allCommittedBacklogItems()) {
            assertTrue(scheduledBacklogItem.backlogItemId().equals(backlogItem1.backlogItemId()) ||
                    scheduledBacklogItem.backlogItemId().equals(backlogItem3.backlogItemId()));
        }
    }
}
