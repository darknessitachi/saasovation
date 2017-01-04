package org.infinite.identityaccess.application.command;

/**
 * 改变用户密码命令
 * 
 * @author Darkness
 * @date 2014-5-28 下午8:29:18
 * @version V1.0
 */
public class ChangeUserPasswordCommand {
	
    private String tenantId;
    private String username;
    private String currentPassword;
    private String changedPassword;

    public ChangeUserPasswordCommand(String tenantId, String aUsername,
            String aCurrentPassword, String aChangedPassword) {

        super();

        this.tenantId = tenantId;
        this.username = aUsername;
        this.currentPassword = aCurrentPassword;
        this.changedPassword = aChangedPassword;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentPassword() {
        return this.currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getChangedPassword() {
        return this.changedPassword;
    }

    public void setChangedPassword(String changedPassword) {
        this.changedPassword = changedPassword;
    }
}
