package com.abigdreamer.saasovation.agilepm.domain.model;

import org.iq80.leveldb.DB;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.EventTrackingTestCase;
import com.abigdreamer.saasovation.agilepm.port.adapter.persistence.LevelDBDatabasePath;
import com.rapidark.framework.persistence.leveldb.LevelDBProvider;
import com.rapidark.framework.persistence.leveldb.LevelDBUnitOfWork;


/**
 * 领域测试基类
 * 
 * @author Darkness
 * @date 2014-5-29 下午2:28:57
 * @version V1.0
 */
public abstract class DomainTest extends EventTrackingTestCase {

    protected DB database;
    
    @Override
    protected void setUp() throws Exception {

        System.out.println(">>>>>>>>>>>>>>>>>>>> " + this.getName());

        DomainEventPublisher.instance().reset();

        this.database = LevelDBProvider.instance().databaseFrom(LevelDBDatabasePath.agilePMPath());

        LevelDBProvider.instance().purge(this.database);

        LevelDBUnitOfWork.start(this.database);

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {

        System.out.println("<<<<<<<<<<<<<<<<<<<< (done)");

        LevelDBProvider.instance().purge(this.database);

        super.tearDown();
    }
}
