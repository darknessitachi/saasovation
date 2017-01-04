package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 任务预估剩余时间完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:54:41
 * @version V1.0
 */
public class TaskHoursRemainingEstimated implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private int hoursRemaining;
    private Date occurredOn;
    private TaskId taskId;
    private TenantId tenantId;

    public TaskHoursRemainingEstimated(
            TenantId aTenantId,
            BacklogItemId aBacklogItemId,
            TaskId aTaskId,
            int aHoursRemaining) {

        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.hoursRemaining = aHoursRemaining;
        this.occurredOn = new Date();
        this.taskId = aTaskId;
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public int eventVersion() {
        return this.eventVersion;
    }

    public int hoursRemaining() {
        return this.hoursRemaining;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TaskId taskId() {
        return this.taskId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
