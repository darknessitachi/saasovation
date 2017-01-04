package org.infinite.identityaccess.domain.event.identity;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 人员名称改变
 * 
 * @author Darkness
 * @date 2014-5-27 下午9:17:05
 * @version V1.0
 */
public class PersonNameChanged implements DomainEvent {

    private int eventVersion;
    private FullName name;
    private Date occurredOn;
    private TenantId tenantId;
    private String username;

    public PersonNameChanged(TenantId aTenantId, String aUsername, FullName aName) {
        super();

        this.eventVersion = 1;
        this.name = aName;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.username = aUsername;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public FullName name() {
        return this.name;
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
