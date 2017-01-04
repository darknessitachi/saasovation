package org.infinite.identityaccess.domain.event.identity;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 组组成员删除
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:41:14
 * @version V1.0
 */
public class GroupGroupRemoved implements DomainEvent {

    private int eventVersion;
    private String groupName;
    private String nestedGroupName;
    private Date occurredOn;
    private TenantId tenantId;

    public GroupGroupRemoved(TenantId aTenantId, String aGroupName, String aNestedGroupName) {
        super();

        this.eventVersion = 1;
        this.groupName = aGroupName;
        this.nestedGroupName = aNestedGroupName;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String groupName() {
        return this.groupName;
    }

    public String nestedGroupName() {
        return this.nestedGroupName;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
