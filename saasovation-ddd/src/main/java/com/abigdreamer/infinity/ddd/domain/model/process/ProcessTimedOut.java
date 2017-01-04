package com.abigdreamer.infinity.ddd.domain.model.process;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;


public class ProcessTimedOut implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private ProcessId processId;
    private int retryCount;
    private String tenantId;
    private int totalRetriesPermitted;

    public ProcessTimedOut(
            String aTenantId,
            ProcessId aProcessId,
            int aTotalRetriesPermitted,
            int aRetryCount) {
        super();

        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.processId = aProcessId;
        this.retryCount = aRetryCount;
        this.tenantId = aTenantId;
        this.totalRetriesPermitted = aTotalRetriesPermitted;
    }

    public boolean allowsRetries() {
        return this.totalRetriesPermitted() > 0;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public boolean hasFullyTimedOut() {
        return !this.allowsRetries() || this.totalRetriesReached();
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public ProcessId processId() {
        return processId;
    }

    public int retryCount() {
        return retryCount;
    }

    public String tenantId() {
        return tenantId;
    }

    public int totalRetriesPermitted() {
        return totalRetriesPermitted;
    }

    public boolean totalRetriesReached() {
        return this.retryCount() >= this.totalRetriesPermitted();
    }
}
