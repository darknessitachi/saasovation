package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 分配任务志愿者完毕
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:42:02
 * @version V1.0
 */
public class TaskVolunteerAssigned implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private TaskId taskId;
    private TenantId tenantId;
    private String volunteerMemberId;

    public TaskVolunteerAssigned(
            TenantId aTenantId,
            BacklogItemId aBacklogItemId,
            TaskId aTaskId,
            String aVolunteerMemberId) {

        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.taskId = aTaskId;
        this.tenantId = aTenantId;
        this.volunteerMemberId = aVolunteerMemberId;
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

    public TaskId taskId() {
        return this.taskId;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public String volunteerMemberId() {
        return this.volunteerMemberId;
    }
}
