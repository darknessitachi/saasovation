package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 描述任务
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:49:08
 * @version V1.0
 */
public class TaskDescribed implements DomainEvent {

    private BacklogItemId backlogItemId;
    private String description;
    private int eventVersion;
    private Date occurredOn;
    private TaskId taskId;
    private TenantId tenantId;

    public TaskDescribed(
            TenantId aTenantId,
            BacklogItemId aBacklogItemId,
            TaskId aTaskId,
            String aDescription) {

        super();

        this.backlogItemId = aBacklogItemId;
        this.description = aDescription;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.taskId = aTaskId;
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public String description() {
        return this.description;
    }

    public int eventVersion() {
        return this.eventVersion;
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
