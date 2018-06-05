package com.abigdreamer.infinity.ddd.notification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.TestableDomainEvent;
import com.abigdreamer.infinity.ddd.notification.application.NotificationApplicationService;
import com.rapidark.framework.persistence.CleanableStore;


/**
 * 通知应用服务测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:34:52
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={
		"classpath:common/applicationContext-hibernate.xml",
		"classpath:common/applicationContext-common-test.xml"}) 
@TransactionConfiguration(transactionManager="transactionManager")
public class NotificationApplicationServiceTest {

	@Resource(name="eventStore")
    protected EventStore eventStore;
	@Resource(name="notificationApplicationService")
    private NotificationApplicationService notificationApplicationService;
	@Resource(name="notificationPublisher")
    private NotificationPublisher notificationPublisher;

	@Test
	@Transactional
    public void testCurrentNotificationLog() throws Exception {
        NotificationLog log =
                this.notificationApplicationService.currentNotificationLog();

        assertTrue(NotificationLogFactory.notificationsPerLog() >= log.totalNotifications());
        assertTrue(eventStore.countStoredEvents() >= log.totalNotifications());
        assertFalse(log.hasNextNotificationLog());
        assertTrue(log.hasPreviousNotificationLog());
        assertFalse(log.isArchived());
    }

	@Test
	@Transactional
    public void testNotificationLog() throws Exception {
        NotificationLogId id = NotificationLogId.first(NotificationLogFactory.notificationsPerLog());

        NotificationLog log = this.notificationApplicationService.notificationLog(id.encoded());

        assertEquals(NotificationLogFactory.notificationsPerLog(), log.totalNotifications());
        assertTrue(eventStore.countStoredEvents() >= log.totalNotifications());
        assertTrue(log.hasNextNotificationLog());
        assertFalse(log.hasPreviousNotificationLog());
        assertTrue(log.isArchived());
    }

	@Test
	@Transactional
    public void testPublishNotifications() throws Exception {
        notificationApplicationService.publishNotifications();

        assertTrue(notificationPublisher.internalOnlyTestConfirmation());
    }

    @Before
    public void setUp() throws Exception {
    	
    	 DomainEventPublisher.instance().reset();

         this.clean((CleanableStore) this.eventStore);

        for (int idx = 1; idx <= 31; ++idx) {
            this.eventStore.append(new TestableDomainEvent(idx, "Event: " + idx));
        }
    }
    
    @After
    public void tearDown() throws Exception {
        this.clean((CleanableStore) this.eventStore);
        System.out.println("<<<<<<<<<<<<<<<<<<<< (done)");
    }
    
    private void clean(CleanableStore aCleanableStore) {
        aCleanableStore.clean();
    }
}
