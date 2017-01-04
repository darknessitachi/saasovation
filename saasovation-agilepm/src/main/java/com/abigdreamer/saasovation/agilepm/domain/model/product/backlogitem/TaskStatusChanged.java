package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 任务状态发生改变
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:44:56
 * @version V1.0
 */
public class TaskStatusChanged implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private TaskStatus status;
    private TaskId taskId;
    private TenantId tenantId;

    public TaskStatusChanged(TenantId aTenantId, BacklogItemId aBacklogItemId, TaskId aTaskId, TaskStatus aStatus) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.status = aStatus;
        this.taskId = aTaskId;
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TaskStatus status() {
        return this.status;
    }

    public TaskId taskId() {
        return this.taskId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
