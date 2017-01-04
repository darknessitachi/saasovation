package org.infinite.identityaccess.domain.event.identity;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 租赁被禁用
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:10:11
 * @version V1.0
 */
public class TenantDeactivated implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;

    public TenantDeactivated(TenantId aTenantId) {
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
