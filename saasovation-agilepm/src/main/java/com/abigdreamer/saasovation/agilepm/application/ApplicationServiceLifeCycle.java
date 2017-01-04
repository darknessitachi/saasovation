package com.abigdreamer.saasovation.agilepm.application;

import org.iq80.leveldb.DB;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.notification.NotificationPublisher;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTrackerStore;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.Exchanges;
import com.abigdreamer.infinity.ddd.port.adapter.notification.SlothMQNotificationPublisher;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.leveldb.LevelDBEventStore;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.leveldb.LevelDBPublishedNotificationTrackerStore;
import com.abigdreamer.infinity.persistence.leveldb.LevelDBProvider;
import com.abigdreamer.infinity.persistence.leveldb.LevelDBUnitOfWork;
import com.abigdreamer.saasovation.agilepm.application.notification.NotificationApplicationService;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBDatabasePath;


/**
 * 应用服务生命周期管理器
 * 
 * @author Darkness
 * @date 2014-5-31 下午6:32:01
 * @version V1.0
 */
public class ApplicationServiceLifeCycle {

    private static final DB database;
    private static final EventStore eventStore;
    private static NotificationApplicationService notificationApplicationService;
    private static NotificationPublisher notificationPublisher;
    private static NotificationPublisherTimer timer;
    private static PublishedNotificationTrackerStore publishedNotificationTrackerStore;

    static {
    	// 初始化LevelDB
        database = LevelDBProvider.instance().databaseFrom(LevelDBDatabasePath.agilePMPath());

        // 初始化事件存储器
        eventStore = new LevelDBEventStore(LevelDBDatabasePath.agilePMPath());

        // 通知发布定时器
        timer = new NotificationPublisherTimer();

        // 发布通知追踪存储器
        publishedNotificationTrackerStore =
                new LevelDBPublishedNotificationTrackerStore(
                        LevelDBDatabasePath.agilePMPath(),
                        "saasovation.agilepm");

//        notificationPublisher =
//                new RabbitMQNotificationPublisher(
//                        eventStore,
//                        publishedNotificationTrackerStore,
//                        Exchanges.AGILEPM_EXCHANGE_NAME);

        notificationPublisher =
                new SlothMQNotificationPublisher(
                        eventStore,
                        publishedNotificationTrackerStore,
                        Exchanges.AGILEPM_EXCHANGE_NAME);

        // 通知应用服务
        notificationApplicationService = new NotificationApplicationService(notificationPublisher);

        timer.start();
    }

    public static void begin() {
        ApplicationServiceLifeCycle.begin(true);
    }

    public static void begin(boolean isListening) {
        if (isListening) {
            ApplicationServiceLifeCycle.listen();
        }

        LevelDBUnitOfWork.start(database);
    }

    public static void fail() {
        LevelDBUnitOfWork.current().rollback();
    }

    public static void fail(RuntimeException anException) {
        ApplicationServiceLifeCycle.fail();

        throw anException;
    }

    public static void fail(Throwable aThrowable) throws Throwable {
        ApplicationServiceLifeCycle.fail();

        throw aThrowable;
    }

    public static void success() {
        LevelDBUnitOfWork.current().commit();
    }

    private static void listen() {
        DomainEventPublisher.instance().reset();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<DomainEvent>() {

                public void handleEvent(DomainEvent aDomainEvent) {
                    eventStore.append(aDomainEvent);
                }

                public Class<DomainEvent> subscribedToEventType() {
                    return DomainEvent.class; // all domain events
                }
            });
    }

    // TODO: need to monitor this...

    private static class NotificationPublisherTimer extends Thread {
        public NotificationPublisherTimer() {
            super();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    notificationApplicationService.publishNotifications();
                } catch (Exception e) {
                    System.out.println("Problem publishing notifications from ApplicationServiceLifeCycle.");
                }

                try {
                    Thread.sleep(100L);
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}
