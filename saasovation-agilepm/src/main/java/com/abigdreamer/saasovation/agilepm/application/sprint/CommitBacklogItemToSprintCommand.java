package com.abigdreamer.saasovation.agilepm.application.sprint;

/**
 * 待办项提交到冲刺完毕
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:16:14
 * @version V1.0
 */
public class CommitBacklogItemToSprintCommand {

    private String backlogItemId;
    private String sprintId;
    private String tenantId;

    public CommitBacklogItemToSprintCommand(
            String tenantId,
            String sprintId,
            String backlogItemId) {

        super();

        this.backlogItemId = backlogItemId;
        this.sprintId = sprintId;
        this.tenantId = tenantId;
    }

    public String getBacklogItemId() {
        return backlogItemId;
    }

    public void setBacklogItemId(String backlogItemId) {
        this.backlogItemId = backlogItemId;
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
