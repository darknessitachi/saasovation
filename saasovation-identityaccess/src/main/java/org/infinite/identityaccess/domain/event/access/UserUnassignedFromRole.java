package org.infinite.identityaccess.domain.event.access;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 取消用户角色分配
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:30:08
 * @version V1.0
 */
public class UserUnassignedFromRole implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private String roleName;
    private TenantId tenantId;
    private String username;

    public UserUnassignedFromRole(
            TenantId aTenantId,
            String aRoleName,
            String aUsername) {

        super();

        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.roleName = aRoleName;
        this.tenantId = aTenantId;
        this.username = aUsername;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
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
