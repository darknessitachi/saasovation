package com.abigdreamer.saasovation.agilepm.domain.model.product.release;

import java.util.Date;


import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductBacklogItemPlanned;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCommonTest;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCreated;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductReleaseScheduled;
import com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem.BacklogItem;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.Release;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ScheduledBacklogItem;


/**
 * 计划发布测试
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:07:40
 * @version V1.0
 */
public class ReleaseTest extends ProductCommonTest {

    public ReleaseTest() {
        super();
    }

    // 测试计划发布日程
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

    // 测试计划发布被激活
    public void testArchived() throws Exception {
        Release release = this.releaseForTest(this.productForTest());

        assertFalse(release.isArchived());

        release.archived(true);

        assertTrue(release.isArchived());
    }

    // 测试修改发布计划描述
    public void testDescribeAs() throws Exception {
        Release release = this.releaseForTest(this.productForTest());

        String changedDescription = "New and improved description.";

        assertFalse(changedDescription.equals(release.description()));

        release.describeAs(changedDescription);

        assertEquals(changedDescription, release.description());
    }

    // 测试计划发布开始时间
    public void testNowBeginsOn() throws Exception {
        Release release = this.releaseForTest(this.productForTest());

        Date date = new Date(new Date().getTime() + (86400000L * 2L));

        assertFalse(date.equals(release.begins()));

        release.nowBeginsOn(date);

        assertEquals(date, release.begins());
    }

    // 测试计划发布结束时间
    public void testNowEndsOn() throws Exception {
        Release release = this.releaseForTest(this.productForTest());

        Date date = new Date(new Date().getTime() + (86400000L * 10L));

        assertFalse(date.equals(release.ends()));

        release.nowEndsOn(date);

        assertEquals(date, release.ends());
    }

    // 测试重命名计划发布
    public void testRename() throws Exception {
        Release release = this.releaseForTest(this.productForTest());

        String changedName = "New Release Name";

        assertFalse(changedName.equals(release.name()));

        release.rename(changedName);

        assertEquals(changedName, release.name());
    }

    // 测试重新排序日程计划中的待办项
    public void testReorderFrom() throws Exception {
        Product product = this.productForTest();
        Release release = this.releaseForTest(product);

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        expectedEvents(5);
        expectedEvent(ProductCreated.class, 1);
        expectedEvent(ProductReleaseScheduled.class, 1);
        expectedEvent(ProductBacklogItemPlanned.class, 3);

        release.schedule(backlogItem1);
        release.schedule(backlogItem2);
        release.schedule(backlogItem3);

        ScheduledBacklogItem scheduledBacklogItem1 = null;
        ScheduledBacklogItem scheduledBacklogItem2 = null;
        ScheduledBacklogItem scheduledBacklogItem3 = null;

        for (ScheduledBacklogItem scheduledBacklogItem : release.allScheduledBacklogItems()) {
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

        release.reorderFrom(backlogItem3.backlogItemId(), 1);

        assertEquals(1, scheduledBacklogItem3.ordering());
        assertEquals(2, scheduledBacklogItem1.ordering());
        assertEquals(3, scheduledBacklogItem2.ordering());
    }

    // 测试待定项的日程安排
    public void testSchedule() throws Exception {
        Product product = this.productForTest();
        Release release = this.releaseForTest(product);

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        expectedEvents(5);
        expectedEvent(ProductCreated.class, 1);
        expectedEvent(ProductReleaseScheduled.class, 1);
        expectedEvent(ProductBacklogItemPlanned.class, 3);

        release.schedule(backlogItem1);
        release.schedule(backlogItem2);
        release.schedule(backlogItem3);

        for (ScheduledBacklogItem scheduledBacklogItem : release.allScheduledBacklogItems()) {
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

    // 测试取消待定项的日程安排
    public void testUnschedule() throws Exception {
        Product product = this.productForTest();
        Release release = this.releaseForTest(product);

        BacklogItem backlogItem1 = this.backlogItem1ForTest(product);
        BacklogItem backlogItem2 = this.backlogItem2ForTest(product);
        BacklogItem backlogItem3 = this.backlogItem3ForTest(product);

        release.schedule(backlogItem1);
        release.schedule(backlogItem2);
        release.schedule(backlogItem3);

        assertEquals(3, release.allScheduledBacklogItems().size());

        release.unschedule(backlogItem2);

        assertEquals(2, release.allScheduledBacklogItems().size());

        for (ScheduledBacklogItem scheduledBacklogItem : release.allScheduledBacklogItems()) {
            assertTrue(scheduledBacklogItem.backlogItemId().equals(backlogItem1.backlogItemId()) ||
                    scheduledBacklogItem.backlogItemId().equals(backlogItem3.backlogItemId()));
        }
    }
}
