package org.infinite.identityaccess.domain.event.identity;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import org.infinite.identityaccess.domain.model.identity.ContactInformation;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 个人联系信息发生改变
 * 
 * @author Darkness
 * @date 2014-5-27 下午9:14:52
 * @version V1.0
 */
public class PersonContactInformationChanged implements DomainEvent {

    private ContactInformation contactInformation;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;
    private String username;

    public PersonContactInformationChanged(
            TenantId aTenantId,
            String aUsername,
            ContactInformation aContactInformation) {

        super();

        this.contactInformation = aContactInformation;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
        this.username = aUsername;
    }

    public ContactInformation contactInformation() {
        return this.contactInformation;
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
