package org.infinite.identityaccess.domain.event.access;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 用户分配给角色
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:24:01
 * @version V1.0
 */
public class UserAssignedToRole implements DomainEvent {

    private String emailAddress;
    private int eventVersion;
    private String firstName;
    private String lastName;
    private Date occurredOn;
    private String roleName;
    private TenantId tenantId;
    private String username;

    public UserAssignedToRole(
            TenantId aTenantId,
            String aRoleName,
            String aUsername,
            String aFirstName,
            String aLastName,
            String anEmailAddress) {

        super();

        this.emailAddress = anEmailAddress;
        this.eventVersion = 1;
        this.firstName = aFirstName;
        this.lastName = aLastName;
        this.occurredOn = new Date();
        this.roleName = aRoleName;
        this.tenantId = aTenantId;
        this.username = aUsername;
    }

    public String emailAddress() {
        return this.emailAddress;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public String roleName() {
        return this.roleName;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public String username() {
        return this.username;
    }
}
