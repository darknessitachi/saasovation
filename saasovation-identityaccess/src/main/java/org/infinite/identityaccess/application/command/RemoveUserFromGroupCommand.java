package org.infinite.identityaccess.application.command;

/**
 * 删除组用户成员命令
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:11:38
 * @version V1.0
 */
public class RemoveUserFromGroupCommand {

    private String tenantId;
    private String groupName;
    private String username;

    public RemoveUserFromGroupCommand(String tenantId, String groupName, String username) {
        super();

        this.tenantId = tenantId;
        this.groupName = groupName;
        this.username = username;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
