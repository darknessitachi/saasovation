package org.infinite.identityaccess.application.command;

/**
 * 禁用租赁命令
 * 
 * @author Darkness
 * @date 2014-5-28 下午8:20:17
 * @version V1.0
 */
public class DeactivateTenantCommand {
	
    private String tenantId;

    public DeactivateTenantCommand(String tenantId) {
        super();

        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
