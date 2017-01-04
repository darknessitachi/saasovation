package com.saasovation.common;

import junit.framework.TestCase;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public abstract class CommonTestCase extends TestCase {

    protected ApplicationContext applicationContext;

    public CommonTestCase() {
        super();
    }

    protected void setUp() throws Exception {

        DomainEventPublisher.instance().reset();

        this.applicationContext = new ClassPathXmlApplicationContext("applicationContext-common.xml");

        System.out.println(">>>>>>>>>>>>>>>>>>>> (start)" + this.getName());

        super.setUp();
    }

    protected void tearDown() throws Exception {

        System.out.println("<<<<<<<<<<<<<<<<<<<< (end)");

        super.tearDown();
    }

}
