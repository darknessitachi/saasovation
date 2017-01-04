package org.infinite.identityaccess.domain.event.identity;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 用户密码发生改变
 * 
 * @author Darkness
 * @date 2014-5-27 下午8:26:45
 * @version V1.0
 */
public class UserPasswordChanged implements DomainEvent {

    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;
    private String username;

    public UserPasswordChanged(TenantId aTenantId, String aUsername) {
        super();

        this.eventVersion = 1;
        this.occurredOn = new Date();
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

    public TenantId tenantId() {
        return this.tenantId;
    }

    public String username() {
        return this.username;
    }
}
