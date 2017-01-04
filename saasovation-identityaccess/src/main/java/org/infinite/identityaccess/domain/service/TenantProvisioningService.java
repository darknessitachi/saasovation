package org.infinite.identityaccess.domain.service;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.event.identity.TenantAdministratorRegistered;
import org.infinite.identityaccess.domain.event.identity.TenantProvisioned;
import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.ContactInformation;
import org.infinite.identityaccess.domain.model.identity.EmailAddress;
import org.infinite.identityaccess.domain.model.identity.Enablement;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.Person;
import org.infinite.identityaccess.domain.model.identity.PostalAddress;
import org.infinite.identityaccess.domain.model.identity.RegistrationInvitation;
import org.infinite.identityaccess.domain.model.identity.Telephone;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.repository.RoleRepository;
import org.infinite.identityaccess.domain.repository.TenantRepository;
import org.infinite.identityaccess.domain.repository.UserRepository;


/**
 * 租赁准备服务
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:10:39
 * @version V1.0
 */
public class TenantProvisioningService {

    private RoleRepository roleRepository;
    private TenantRepository tenantRepository;
    private UserRepository userRepository;

    public TenantProvisioningService(
            TenantRepository aTenantRepository,
            UserRepository aUserRepository,
            RoleRepository aRoleRepository) {

        super();

        this.roleRepository = aRoleRepository;
        this.tenantRepository = aTenantRepository;
        this.userRepository = aUserRepository;
    }

    // 准备租赁
    public Tenant provisionTenant(
            String aTenantName,
            String aTenantDescription,
            FullName anAdministorName,
            EmailAddress anEmailAddress,
            PostalAddress aPostalAddress,
            Telephone aPrimaryTelephone,
            Telephone aSecondaryTelephone) {

        try {
            Tenant tenant = new Tenant(
                        this.tenantRepository().nextIdentity(),
                        aTenantName,
                        aTenantDescription,
                        true); // must be active to register admin

            this.tenantRepository().add(tenant);

            this.registerAdministratorFor(
                    tenant,
                    anAdministorName,
                    anEmailAddress,
                    aPostalAddress,
                    aPrimaryTelephone,
                    aSecondaryTelephone);

            DomainEventPublisher
                .instance()
                .publish(new TenantProvisioned(
                        tenant.tenantId()));

            return tenant;

        } catch (Throwable t) {
            throw new IllegalStateException(
                    "Cannot provision tenant because: "
                    + t.getMessage());
        }
    }

    private void registerAdministratorFor(
            Tenant aTenant,
            FullName anAdministorName,
            EmailAddress anEmailAddress,
            PostalAddress aPostalAddress,
            Telephone aPrimaryTelephone,
            Telephone aSecondaryTelephone) {

        RegistrationInvitation invitation =
                aTenant.offerRegistrationInvitation("init").openEnded();

        String strongPassword =
                DomainRegistry
                    .passwordService()
                    .generateStrongPassword();

        User admin =
            aTenant.registerUser(
                    invitation.invitationId(),
                    "admin",
                    strongPassword,
                    Enablement.indefiniteEnablement(),
                    new Person(
                            aTenant.tenantId(),
                            anAdministorName,
                            new ContactInformation(
                                    anEmailAddress,
                                    aPostalAddress,
                                    aPrimaryTelephone,
                                    aSecondaryTelephone)));

        aTenant.withdrawInvitation(invitation.invitationId());

        this.userRepository().add(admin);

        Role adminRole =
            aTenant.provisionRole(
                    "Administrator",
                    "Default " + aTenant.name() + " administrator.");

        adminRole.assignUser(admin);

        this.roleRepository().add(adminRole);

        DomainEventPublisher.instance().publish(
                new TenantAdministratorRegistered(
                        aTenant.tenantId(),
                        aTenant.name(),
                        anAdministorName,
                        anEmailAddress,
                        admin.username(),
                        strongPassword));
    }

    private RoleRepository roleRepository() {
        return this.roleRepository;
    }

    private TenantRepository tenantRepository() {
        return this.tenantRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }
}
