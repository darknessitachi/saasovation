//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.abigdreamer.infinity.ddd.port.adapter.persistence.leveldb;

import org.iq80.leveldb.DB;

import com.abigdreamer.infinity.ddd.event.EventStore;
import com.abigdreamer.infinity.ddd.event.MockEventStore;
import com.abigdreamer.infinity.ddd.notification.NotificationLog;
import com.abigdreamer.infinity.ddd.notification.NotificationLogFactory;
import com.abigdreamer.infinity.ddd.notification.PublishedNotificationTracker;
import com.rapidark.framework.persistence.leveldb.LevelDBProvider;
import com.rapidark.framework.persistence.leveldb.LevelDBUnitOfWork;

import junit.framework.TestCase;


public class LevelDBPublishedNotificationTrackerStoreTest
        extends TestCase {

	private static final String TEST_DATABASE = LevelDBTest.TEST_DATABASE;

    private DB database;
    private EventStore eventStore;
    private LevelDBPublishedNotificationTrackerStore publishedNotificationTrackerStore;

    public void testTrackMostRecentPublishedNotification() throws Exception {
        NotificationLogFactory factory = new NotificationLogFactory(eventStore);
        NotificationLog log = factory.createCurrentNotificationLog();

        this.publishedNotificationTrackerStore
            .trackMostRecentPublishedNotification(
                    new PublishedNotificationTracker("saasOvation_test"),
                    log.notifications());

        LevelDBUnitOfWork.current().commit();

        PublishedNotificationTracker tracker =
                this.publishedNotificationTrackerStore
                    .publishedNotificationTracker();

        int notifications = log.notifications().size();

        assertNotNull(tracker);
        assertEquals(log.notifications().get(notifications - 1).notificationId(),
                tracker.mostRecentPublishedNotificationId());
    }

    @Override
    protected void setUp() throws Exception {
        this.database = LevelDBProvider.instance().databaseFrom(TEST_DATABASE);

        LevelDBProvider.instance().purge(this.database);

        this.eventStore = new MockEventStore();

        assertNotNull(eventStore);

        this.publishedNotificationTrackerStore =
                new LevelDBPublishedNotificationTrackerStore(
                        TEST_DATABASE,
                        "saasOvation_test");

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        LevelDBProvider.instance().purge(this.database);

        super.tearDown();
    }
}
