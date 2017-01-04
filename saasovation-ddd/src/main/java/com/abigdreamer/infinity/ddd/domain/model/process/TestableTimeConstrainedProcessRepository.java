package com.abigdreamer.infinity.ddd.domain.model.process;

import javax.persistence.PersistenceException;

import org.hibernate.Query;
import org.hibernate.Session;

import com.abigdreamer.infinity.persistence.hibernate.HibernateSessionSupport;


public class TestableTimeConstrainedProcessRepository
    extends HibernateSessionSupport {

    public TestableTimeConstrainedProcessRepository() {
        super();
    }

    public void add(TestableTimeConstrainedProcess aTestableTimeConstrainedProcess) {
        try {
            this.session().persist(aTestableTimeConstrainedProcess);
        } catch (PersistenceException e) {
            throw new IllegalStateException("Either TestableTimeConstrainedProcess is not unique or another constraint has been violated.", e);
        }
    }

    public TestableTimeConstrainedProcess processOfId(ProcessId aProcessId) {
        Query query =
                this.session().createQuery(
                    "from TestableTimeConstrainedProcess as ttcp where ttcp.processId = ?");

        query.setParameter(0, aProcessId);

        return (TestableTimeConstrainedProcess) query.uniqueResult();
    }

    public Session getSession() {
        return this.session();
    }
}
