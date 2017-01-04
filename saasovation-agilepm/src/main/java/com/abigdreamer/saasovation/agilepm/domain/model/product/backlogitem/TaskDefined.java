package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  任务定义完成
 * 
 * @author Darkness
 * @date 2014-5-8 下午8:08:29 
 * @version V1.0
 */
public class TaskDefined implements DomainEvent {

    private BacklogItemId backlogItemId;
    private String description;
    private int eventVersion;
    private int hoursRemaining;
    private String name;
    private Date occurredOn;
    private TaskId taskId;
    private TenantId tenantId;
    private String volunteerMemberId;

    public TaskDefined(
            TenantId aTenantId,
            BacklogItemId aBacklogItemId,
            TaskId aTaskId,
            String aVolunteerMemberId,
            String aName,
            String aDescription,
            int aHoursRemaining) {

        super();

        this.backlogItemId = aBacklogItemId;
        this.description = aDescription;
        this.eventVersion = 1;
        this.hoursRemaining = aHoursRemaining;
        this.name = aName;
        this.occurredOn = new Date();
        this.taskId = aTaskId;
        this.tenantId = aTenantId;
        this.volunteerMemberId = aVolunteerMemberId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    public String description() {
        return this.description;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public int hoursRemaining() {
        return this.hoursRemaining;
    }

    public String name() {
        return this.name;
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
