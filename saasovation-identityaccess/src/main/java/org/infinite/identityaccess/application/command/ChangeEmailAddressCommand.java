package org.infinite.identityaccess.application.command;

/**
 * 改变用户Email命令
 * 
 * @author Darkness
 * @date 2014-5-28 下午8:24:52
 * @version V1.0
 */
public class ChangeEmailAddressCommand {
	
    private String tenantId;
    private String username;
    private String emailAddress;

    public ChangeEmailAddressCommand(String tenantId, String username, String emailAddress) {
        super();

        this.tenantId = tenantId;
        this.username = username;
        this.emailAddress = emailAddress;
    }

    public ChangeEmailAddressCommand() {
        super();
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

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
