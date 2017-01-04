package org.infinite.identityaccess.domain.model.identity;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.event.identity.TenantAdministratorRegistered;
import org.infinite.identityaccess.domain.event.identity.TenantProvisioned;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.EmailAddress;
import org.infinite.identityaccess.domain.model.identity.Enablement;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.PostalAddress;
import org.infinite.identityaccess.domain.model.identity.RegistrationInvitation;
import org.infinite.identityaccess.domain.model.identity.Telephone;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 租赁测试
 * 
 * @author Darkness
 * @date 2014-5-27 下午9:21:41
 * @version V1.0
 */
public class TenantTest extends IdentityAccessTest {

    private boolean handled1;
    private boolean handled2;

    public TenantTest() {
        super();
    }

    // 测试准备租赁
    public void testProvisionTenant() throws Exception {

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<TenantProvisioned>() {
                public void handleEvent(TenantProvisioned aDomainEvent) {
                    handled1 = true;
                }
                public Class<TenantProvisioned> subscribedToEventType() {
                    return TenantProvisioned.class;
                }
            });

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<TenantAdministratorRegistered>() {
                public void handleEvent(TenantAdministratorRegistered aDomainEvent) {
                    handled2 = true;
                }
                public Class<TenantAdministratorRegistered> subscribedToEventType() {
                    return TenantAdministratorRegistered.class;
                }
            });

        Tenant tenant =
            DomainRegistry
                .tenantProvisioningService()
                .provisionTenant(
                        FIXTURE_TENANT_NAME,
                        FIXTURE_TENANT_DESCRIPTION,
                        new FullName("John", "Doe"),
                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS),
                        new PostalAddress(
                                "123 Pearl Street",
                                "Boulder",
                                "CO",
                                "80301",
                                "US"),
                        new Telephone("303-555-1210"),
                        new Telephone("303-555-1212"));

        assertTrue(handled1);
        assertTrue(handled2);

        assertNotNull(tenant.tenantId());
        assertNotNull(tenant.tenantId().id());
        assertEquals(36, tenant.tenantId().id().length());
        assertEquals(FIXTURE_TENANT_NAME, tenant.name());
        assertEquals(FIXTURE_TENANT_DESCRIPTION, tenant.description());
    }

    public void testCreateOpenEndedInvitation() throws Exception {

        Tenant tenant = this.tenantAggregate();

        tenant
            .offerRegistrationInvitation("Open-Ended")
            .openEnded();

        assertNotNull(tenant.redefineRegistrationInvitationAs("Open-Ended"));
    }

    // 测试放开结束日期的邀请可用
    public void testOpenEndedInvitationAvailable() throws Exception {

        Tenant tenant = this.tenantAggregate();

        tenant
            .offerRegistrationInvitation("Open-Ended")
            .openEnded();

        assertTrue(tenant.isRegistrationAvailableThrough("Open-Ended"));
    }

    // 测试近期结束的邀请可用
    public void testClosedEndedInvitationAvailable() throws Exception {

        Tenant tenant = this.tenantAggregate();

        tenant
            .offerRegistrationInvitation("Today-and-Tomorrow")
            .startingOn(this.today())
            .until(this.tomorrow());

        assertTrue(tenant.isRegistrationAvailableThrough("Today-and-Tomorrow"));
    }

    // 测试近期的邀请不可用
    public void testClosedEndedInvitationNotAvailable() throws Exception {

        Tenant tenant = this.tenantAggregate();

        tenant
            .offerRegistrationInvitation("Tomorrow-and-Day-After-Tomorrow")
            .startingOn(this.tomorrow())
            .until(this.dayAfterTomorrow());

        assertFalse(tenant.isRegistrationAvailableThrough("Tomorrow-and-Day-After-Tomorrow"));
    }

    // 测试可用邀请描述
    public void testAvailableInivitationDescriptor() throws Exception {

        Tenant tenant = this.tenantAggregate();

        tenant
            .offerRegistrationInvitation("Open-Ended")
            .openEnded();

        tenant
            .offerRegistrationInvitation("Today-and-Tomorrow")
            .startingOn(this.today())
            .until(this.tomorrow());

        assertEquals(tenant.allAvailableRegistrationInvitations().size(), 2);
    }

	// 测试不可用邀请描述
    public void testUnavailableInivitationDescriptor() throws Exception {

        Tenant tenant = this.tenantAggregate();

        tenant
            .offerRegistrationInvitation("Tomorrow-and-Day-After-Tomorrow")
            .startingOn(this.tomorrow())
            .until(this.dayAfterTomorrow());

        assertEquals(tenant.allUnavailableRegistrationInvitations().size(), 1);
    }

    // 测试注册用户
    public void testRegisterUser() throws Exception {

        Tenant tenant = this.tenantAggregate();

        RegistrationInvitation registrationInvitation = this.registrationInvitationEntity(tenant);

        User user =
            tenant.registerUser(
                    registrationInvitation.invitationId(),
                    FIXTURE_USERNAME,
                    FIXTURE_PASSWORD,
                    new Enablement(true, null, null),
                    this.personEntity(tenant));

        assertNotNull(user);

        DomainRegistry.userRepository().add(user);

        assertNotNull(user.enablement());
        assertNotNull(user.person());
        assertNotNull(user.userDescriptor());
    }
}
