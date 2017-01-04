package org.infinite.identityaccess.domain.event.access;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 组分配给角色
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:23:06
 * @version V1.0
 */
public class GroupAssignedToRole implements DomainEvent {

    private int eventVersion;
    private String groupName;
    private Date occurredOn;
    private String roleName;
    private TenantId tenantId;

    public GroupAssignedToRole(TenantId aTenantId, String aRoleName, String aGroupName) {
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
