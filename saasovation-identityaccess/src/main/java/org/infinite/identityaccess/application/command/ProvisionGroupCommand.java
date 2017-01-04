package org.infinite.identityaccess.application.command;

/**
 * 准备组命令
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:17:23
 * @version V1.0
 */
public class ProvisionGroupCommand {

    private String description;
    private String groupName;
    private String tenantId;

    public ProvisionGroupCommand(
            String tenantId,
            String groupName,
            String description) {

        super();

        this.description = description;
        this.groupName = groupName;
        this.tenantId = tenantId;
    }

    public ProvisionGroupCommand() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
