package org.infinite.identityaccess.domain.event.access;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 取消组的角色分配
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:13:13
 * @version V1.0
 */
public class GroupUnassignedFromRole implements DomainEvent {

    private int eventVersion;
    private String groupName;
    private Date occurredOn;
    private String roleName;
    private TenantId tenantId;

    public GroupUnassignedFromRole(TenantId aTenantId, String aRoleName, String aGroupName) {
        super();

        this.eventVersion = 1;
        this.groupName = aGroupName;
        this.occurredOn = new Date();
        this.roleName = aRoleName;
        this.tenantId = aTenantId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String groupName() {
        return this.groupName;
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
}
