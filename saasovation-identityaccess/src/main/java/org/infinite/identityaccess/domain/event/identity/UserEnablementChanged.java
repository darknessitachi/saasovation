package org.infinite.identityaccess.domain.event.identity;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.Enablement;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 用户可用状态发生改变
 * 
 * @author Darkness
 * @date 2014-5-27 下午8:17:43
 * @version V1.0
 */
public class UserEnablementChanged implements DomainEvent {

    private Enablement enablement;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;
    private String username;

    public UserEnablementChanged(
            TenantId aTenantId,
            String aUsername,
            Enablement anEnablement) {

        super();

        this.enablement = anEnablement;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.username = aUsername;
    }

    public Enablement enablement() {
        return this.enablement;
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

    public String username() {
        return this.username;
    }
}
