package com.abigdreamer.infinity.ddd.notification;

import static org.junit.Assert.*;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.MockEventStore;
import com.abigdreamer.infinity.ddd.notification.NotificationLog;
import com.abigdreamer.infinity.ddd.notification.NotificationLogFactory;
import com.abigdreamer.infinity.ddd.notification.NotificationLogId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 通知日志测试
 *  
 * @author Darkness
 * @date 2014-12-22 下午11:09:28
 * @version V1.0
 * @since ark 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:common/applicationContext-*.xml"}) 
public class NotificationLogTest {

	@Before
	public void init() {
		DomainEventPublisher.instance().reset();
	}

	// 日志工厂创建当前通知日志
	@Test
    public void currentNotificationLogFromFactory() throws Exception {
		// 创建测试事件仓储
        EventStore eventStore = this.eventStore();
        // 准备通知日志工厂
        NotificationLogFactory factory = new NotificationLogFactory(eventStore);
        // 创建当前日志(根据事件仓储中的存储事件生成通知)
        NotificationLog log = factory.createCurrentNotificationLog();

        // 一个日志中包含的通知数量肯定<=日志包含的最大通知数
        assertTrue(NotificationLogFactory.notificationsPerLog() >= log.totalNotifications());
        // 一个日志中包含的通知数量肯定<=事件仓储包含的所有通知数量
        assertTrue(eventStore.countStoredEvents() >= log.totalNotifications());
        // 当前日志没有下一个日志
        assertFalse(log.hasNextNotificationLog());
        // 当前日志有前一个日志(测试事件仓储中生成的存储事件数量最小为每个日志包含通知的数量+1)
        assertTrue(log.hasPreviousNotificationLog());
        // 当前通知日志为非存档日志
        assertFalse(log.isArchived());
    }

	// 日志工厂创建第一个通知日志
	@Test
    public void firstNotificationLogFromFactory() throws Exception {
        EventStore eventStore = this.eventStore();
        NotificationLogFactory factory = new NotificationLogFactory(eventStore);
        
        // 准备第一个通知日志Id
        NotificationLogId id = NotificationLogId.first(NotificationLogFactory.notificationsPerLog());
        // 根据通知日志Id创建日志(根据事件仓储中的存储事件生成通知)
        NotificationLog log = factory.createNotificationLog(id);

        // 第一个通知日志包含的通知数量等于每个通知日志的通知数量(测试事件仓储中生成的存储事件数量最小为每个日志包含通知的数量+1)
        assertEquals(NotificationLogFactory.notificationsPerLog(), log.totalNotifications());
        // 第一个日志中包含的通知数量肯定<=事件仓储包含的所有通知数量
        assertTrue(eventStore.countStoredEvents() >= log.totalNotifications());
        // 第一个通知日志有下一个通知日志
        assertTrue(log.hasNextNotificationLog());
        // 第一个通知日志没有前一个通知日志
        assertFalse(log.hasPreviousNotificationLog());
        // 第一个通知日志为归档日志
        assertTrue(log.isArchived());
    }

	// 创建当前通知日志的前一个通知日志
	@Test
    public void previousOfCurrentNotificationLogFromFactory() throws Exception {
        EventStore eventStore = this.eventStore();
        long totalEvents = eventStore.countStoredEvents();
        // 存储的事件总数大于每个通知日志数量*2，当前日志的前一个日志就有前一个日志，否则没有前一个日志
        boolean shouldBePrevious = totalEvents > (NotificationLogFactory.notificationsPerLog() * 2);
        NotificationLogFactory factory = new NotificationLogFactory(eventStore);
        NotificationLog log = factory.createCurrentNotificationLog();

        NotificationLogId previousId = log.decodedPreviousNotificationLogId();
        log = factory.createNotificationLog(previousId);

        assertEquals(NotificationLogFactory.notificationsPerLog(), log.totalNotifications());
        assertTrue(totalEvents >= log.totalNotifications());
        assertTrue(log.hasNextNotificationLog());
        assertEquals(shouldBePrevious, log.hasPreviousNotificationLog());
        assertTrue(log.isArchived());
    }

	// 当前日志可编码&解码导航到前一个日志Id、下一个日志Id
	@Test
    public void encodedWithDecodedNavigationIds() throws Exception {
        EventStore eventStore = this.eventStore();
        NotificationLogFactory factory = new NotificationLogFactory(eventStore);
        NotificationLog log = factory.createCurrentNotificationLog();

        String currentId = log.notificationLogId();
        NotificationLogId decodedCurrentLogId = log.decodedNotificationLogId();
        assertEquals(log.decodedNotificationLogId(), new NotificationLogId(currentId));

        String previousId = log.previousNotificationLogId();
        NotificationLogId decodedPreviousLogId = log.decodedPreviousNotificationLogId();
        assertEquals(decodedPreviousLogId, new NotificationLogId(previousId));
        log = factory.createNotificationLog(log.decodedPreviousNotificationLogId());

        String nextId = log.nextNotificationLogId();
        NotificationLogId decodedNextLogId = log.decodedNextNotificationLogId();
        assertEquals(decodedNextLogId, new NotificationLogId(nextId));
        assertEquals(decodedCurrentLogId, decodedNextLogId);
    }

    private EventStore eventStore() {
        EventStore eventStore = new MockEventStore();

        assertNotNull(eventStore);

        return eventStore;
    }
}
