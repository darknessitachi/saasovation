package com.saasovation.collaboration.port.adapter.persistence;

import com.abigdreamer.infinity.ddd.event.sourcing.EventStore;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.eventsourcing.leveldb.LevelDBEventStore;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.eventsourcing.mysql.MySQLJDBCEventStore;


/**
 * 事件存储提供者
 * 
 * @author Darkness
 * @date 2014-5-31 下午1:53:15
 * @version V1.0
 */
public class EventStoreProvider {

    private static final boolean FOR_LEVELDB = true;
    private static final boolean FOR_MYSQL = false;

    private EventStore eventStore;

    public static EventStoreProvider instance() {
        return new EventStoreProvider();
    }

    public EventStore eventStore() {
        return this.eventStore;
    }

    protected EventStoreProvider() {
        super();

        this.initializeLevelDB();

        this.initializeMySQL();
    }

	private void initializeLevelDB() {
		if (FOR_LEVELDB) {
			this.eventStore = LevelDBEventStore.instance(this.getClass().getResource("/").getPath() + "/data/leveldb/iddd_collaboration_es");
		}
	}

    private void initializeMySQL() {
        if (FOR_MYSQL) {
            this.eventStore = MySQLJDBCEventStore.instance();
        }
    }
}
