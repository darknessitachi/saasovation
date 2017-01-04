package org.infinite.identityaccess.application.command;

/**
 * 组添加组成员命令
 * 
 * @author Darkness
 * @date 2014-5-28 下午8:14:33
 * @version V1.0
 */
public class AddGroupToGroupCommand {

    private String tenantId;
    private String childGroupName;
    private String parentGroupName;

    public AddGroupToGroupCommand(String tenantId, String parentGroupName, String childGroupName) {
        super();

        this.tenantId = tenantId;
        this.parentGroupName = parentGroupName;
        this.childGroupName = childGroupName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getChildGroupName() {
        return childGroupName;
    }

    public void setChildGroupName(String childGroupName) {
        this.childGroupName = childGroupName;
    }

    public String getParentGroupName() {
        return parentGroupName;
    }

    public void setParentGroupName(String parentGroupName) {
        this.parentGroupName = parentGroupName;
    }
}
