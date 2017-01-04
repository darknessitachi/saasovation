package com.abigdreamer.saasovation.agilepm.application.product;

import java.util.Date;

public class TimeOutProductDiscussionRequestCommand {

    private String tenantId;
    private String processId;
    private Date timedOutDate;

    public TimeOutProductDiscussionRequestCommand(String tenantId, String processId, Date timedOutDate) {
        super();
        this.tenantId = tenantId;
        this.processId = processId;
        this.timedOutDate = timedOutDate;
    }

    public TimeOutProductDiscussionRequestCommand() {
        super();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Date getTimedOutDate() {
        return timedOutDate;
    }

    public void setTimedOutDate(Date timedOutDate) {
        this.timedOutDate = timedOutDate;
    }
}
