package org.infinite.identityaccess.application.command;

/**
 * 激活租赁命令
 * 
 * @author Darkness
 * @date 2013-12-20 下午4:55:03
 * @version V1.0
 * @since ark 1.0
 */
public class ActivateTenantCommand {
	
	private String tenantId;

	public ActivateTenantCommand(String tenantId) {
		super();

		this.tenantId = tenantId;
	}

	public ActivateTenantCommand() {
		super();
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}
