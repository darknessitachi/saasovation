package org.infinite.identityaccess.domain.event.identity;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 租赁准备完毕
 * 
 * @author Darkness
 * @date 2014-5-27 下午9:25:54
 * @version V1.0
 */
public class TenantProvisioned implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;

    public TenantProvisioned(TenantId aTenantId) {
        super();

        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
