package org.infinite.identityaccess.domain.model;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.persistence.hibernate.HibernateSessionSupport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * 领域测试基类
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:46:44
 * @version V1.0
 */
public abstract class DomainTest extends TestCase {

    protected ApplicationContext applicationContext;
    private HibernateSessionSupport hibernateSessionSupport;
    private Transaction transaction;

    protected DomainTest() {
        super();
    }

    // 获取hibernate session
    protected Session session() {
        Session session = this.hibernateSessionSupport.session();

        return session;
    }

    @Override
    protected void setUp() throws Exception {

        // 构造spring上下文
        applicationContext =
                new ClassPathXmlApplicationContext(
                        new String[] {
                                "applicationContext-identityaccess.xml",
                                "applicationContext-common.xml" });

        // 初始化hibernate session提供器
        this.hibernateSessionSupport = (HibernateSessionSupport) applicationContext.getBean("hibernateSessionSupport");

        this.setTransaction(this.session().beginTransaction());

        DomainEventPublisher.instance().reset();

        System.out.println(">>>>>>>>>>>>>>>>>>>> " + this.getName());

        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {

        this.transaction().rollback();

        this.setTransaction(null);

        this.session().clear();

        System.out.println("<<<<<<<<<<<<<<<<<<<< (done)");

        super.tearDown();
    }

    protected Transaction transaction() {
        return transaction;
    }

    private void setTransaction(Transaction aTransaction) {
        this.transaction = aTransaction;
    }
}
