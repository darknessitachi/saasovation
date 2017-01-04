package org.infinite.identityaccess.domain.model.identity;

import java.util.*;

import com.abigdreamer.infinity.ddd.domain.model.ConcurrencySafeEntity;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import org.infinite.identityaccess.domain.event.access.RoleProvisioned;
import org.infinite.identityaccess.domain.event.identity.GroupProvisioned;
import org.infinite.identityaccess.domain.event.identity.TenantActivated;
import org.infinite.identityaccess.domain.event.identity.TenantDeactivated;
import org.infinite.identityaccess.domain.model.access.*;


/**
 *  租赁
 * 
 * @author Darkness
 * @date 2014-5-10 下午8:47:39 
 * @version V1.0
 */
public class Tenant extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    private boolean active;// 是否激活状态
    private String description;
    private String name;
    private Set<RegistrationInvitation> registrationInvitations;
    private TenantId tenantId;

    public Tenant(TenantId aTenantId, String aName, String aDescription, boolean anActive) {
        this();

        this.setActive(anActive);
        this.setDescription(aDescription);
        this.setName(aName);
        this.setTenantId(aTenantId);
    }

    /**
     *  激活
     * 
     * @author Darkness
     * @date 2014-5-10 下午8:55:51 
     * @version V1.0
     */
    public void activate() {
        if (!this.isActive()) {

            this.setActive(true);

            DomainEventPublisher
                .instance()
                .publish(new TenantActivated(this.tenantId()));
        }
    }

    /**
     *  禁用
     * 
     * @author Darkness
     * @date 2014-5-10 下午8:56:05 
     * @version V1.0
     */
    public void deactivate() {
        if (this.isActive()) {

            this.setActive(false);

            DomainEventPublisher
                .instance()
                .publish(new TenantDeactivated(this.tenantId()));
        }
    }

    public Collection<InvitationDescriptor> allAvailableRegistrationInvitations() {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        return this.allRegistrationInvitationsFor(true);
    }

    public Collection<InvitationDescriptor> allUnavailableRegistrationInvitations() {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        return this.allRegistrationInvitationsFor(false);
    }

    public String description() {
        return this.description;
    }

    public boolean isActive() {
        return this.active;
    }

    /**
     * 判断注册可以通过
     * @param anInvitationIdentifier
     * @return
     */
    public boolean isRegistrationAvailableThrough(String anInvitationIdentifier) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        RegistrationInvitation invitation = this.invitation(anInvitationIdentifier);

        return invitation == null ? false : invitation.isAvailable();
    }

    public String name() {
        return this.name;
    }

    /**
     * 提供一个注册邀请
     * @param aDescription 描述
     * @return 注册邀请
     */
    public RegistrationInvitation offerRegistrationInvitation(String aDescription) {
    	
    		// 确保当前租赁处于激活状态
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        // 确保请求的邀请描述不存在
        this.assertStateFalse(
                this.isRegistrationAvailableThrough(aDescription),
                "Invitation already exists.");

        RegistrationInvitation invitation =
            new RegistrationInvitation(
                    this.tenantId(),
                    UUID.randomUUID().toString().toUpperCase(),
                    aDescription);

        boolean added = this.registrationInvitations().add(invitation);

        this.assertStateTrue(added, "The invitation should have been added.");

        return invitation;
    }

    public Group provisionGroup(String aName, String aDescription) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        Group group = new Group(this.tenantId(), aName, aDescription);

        DomainEventPublisher
            .instance()
            .publish(new GroupProvisioned(
                    this.tenantId(),
                    aName));

        return group;
    }

    public Role provisionRole(String aName, String aDescription) {
        return this.provisionRole(aName, aDescription, false);
    }

    public Role provisionRole(String aName, String aDescription, boolean aSupportsNesting) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        Role role = new Role(this.tenantId(), aName, aDescription, aSupportsNesting);

        DomainEventPublisher
            .instance()
            .publish(new RoleProvisioned(
                    this.tenantId(),
                    aName));

        return role;
    }

    public RegistrationInvitation redefineRegistrationInvitationAs(String anInvitationIdentifier) {
        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        RegistrationInvitation invitation = this.invitation(anInvitationIdentifier);

        if (invitation != null) {
            invitation.redefineAs().openEnded();
        }

        return invitation;
    }

    public User registerUser(
            String anInvitationIdentifier,
            String aUsername,
            String aPassword,
            Enablement anEnablement,
            Person aPerson) {

        this.assertStateTrue(this.isActive(), "Tenant is not active.");

        User user = null;

        if (this.isRegistrationAvailableThrough(anInvitationIdentifier)) {

            // ensure same tenant
            aPerson.setTenantId(this.tenantId());

            user = new User(
                    this.tenantId(),
                    aUsername,
                    aPassword,
                    anEnablement,
                    aPerson);
        }

        return user;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public void withdrawInvitation(String anInvitationIdentifier) {
        RegistrationInvitation invitation =
            this.invitation(anInvitationIdentifier);

        if (invitation != null) {
            this.registrationInvitations().remove(invitation);
        }
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Tenant typedObject = (Tenant) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.name().equals(typedObject.name());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (48123 * 257)
            + this.tenantId().hashCode()
            + this.name().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Tenant [active=" + active + ", description=" + description
                + ", name=" + name + ", tenantId=" + tenantId + "]";
    }

    protected Tenant() {
        super();

        this.setRegistrationInvitations(new HashSet<RegistrationInvitation>(0));
    }

    protected void setActive(boolean anActive) {
        this.active = anActive;
    }

    protected Collection<InvitationDescriptor> allRegistrationInvitationsFor(boolean isAvailable) {
        Set<InvitationDescriptor> allInvitations = new HashSet<InvitationDescriptor>();

        for (RegistrationInvitation invitation : this.registrationInvitations()) {
            if (invitation.isAvailable() == isAvailable) {
                allInvitations.add(invitation.toDescriptor());
            }
        }

        return Collections.unmodifiableSet(allInvitations);
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "The tenant description is required.");
        this.assertArgumentLength(aDescription, 1, 100, "The tenant description must be 100 characters or less.");

        this.description = aDescription;
    }

    protected RegistrationInvitation invitation(String anInvitationIdentifier) {
        for (RegistrationInvitation invitation : this.registrationInvitations()) {
            if (invitation.isIdentifiedBy(anInvitationIdentifier)) {
                return invitation;
            }
        }

        return null;
    }

    protected void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "The tenant name is required.");
        this.assertArgumentLength(aName, 1, 100, "The name must be 100 characters or less.");

        this.name = aName;
    }

    // 注册的邀请
    protected Set<RegistrationInvitation> registrationInvitations() {
        return this.registrationInvitations;
    }

    protected void setRegistrationInvitations(Set<RegistrationInvitation> aRegistrationInvitations) {
        this.registrationInvitations = aRegistrationInvitations;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "TenentId is required.");

        this.tenantId = aTenantId;
    }
}
