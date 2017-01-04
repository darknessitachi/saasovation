package org.infinite.identityaccess.domain.event.access;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 角色准备完毕
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:13:48
 * @version V1.0
 */
public class RoleProvisioned implements DomainEvent {

    private int eventVersion;
    private String name;
    private Date occurredOn;
    private TenantId tenantId;

    public RoleProvisioned(TenantId aTenantId, String aName) {
        super();

        this.eventVersion = 1;
        this.name = aName;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String name() {
        return this.name;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
