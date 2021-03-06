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

package com.saasovation.collaboration.domain.model;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abigdreamer.infinity.ddd.domain.model.EventTrackingTestCase;
import com.abigdreamer.infinity.ddd.port.adapter.persistence.ConnectionProvider;
import com.saasovation.collaboration.StorageCleaner;

public abstract class DomainTest extends EventTrackingTestCase {

    protected ApplicationContext applicationContext;
    protected DataSource dataSource;

    private StorageCleaner storageCleaner;

    protected DomainTest() {
        super();
    }

    protected void setUp() throws Exception {
        if (applicationContext == null) {
            applicationContext =
                    new ClassPathXmlApplicationContext(
                            new String[] {
                                    "applicationContext-collaboration.xml" });
        }

        if (dataSource == null) {
            dataSource = (DataSource) applicationContext.getBean("collaborationDataSource");
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>> " + this.getName());

        storageCleaner = new StorageCleaner(this.dataSource);

        super.setUp();
    }

    protected void tearDown() throws Exception {
        storageCleaner.clean();

        ConnectionProvider.closeConnection();

        System.out.println("<<<<<<<<<<<<<<<<<<<< (done)");

        super.tearDown();
    }
}
