package org.infinite.identityaccess.application;

import junit.framework.TestCase;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.event.EventStore;
import com.github.rapidark.framework.persistence.CleanableStore;

import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.ContactInformation;
import org.infinite.identityaccess.domain.model.identity.EmailAddress;
import org.infinite.identityaccess.domain.model.identity.Enablement;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.Person;
import org.infinite.identityaccess.domain.model.identity.PostalAddress;
import org.infinite.identityaccess.domain.model.identity.RegistrationInvitation;
import org.infinite.identityaccess.domain.model.identity.Telephone;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 应用服务测试基类
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:35:48
 * @version V1.0
 */
public abstract class ApplicationServiceTest extends TestCase {

    protected static final String FIXTURE_GROUP_NAME = "Test Group";
    protected static final String FIXTURE_PASSWORD = "SecretPassword!";
    protected static final String FIXTURE_ROLE_NAME = "Test Role";
    protected static final String FIXTURE_TENANT_DESCRIPTION = "This is a test tenant.";
    protected static final String FIXTURE_TENANT_NAME = "Test Tenant";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS = "jdoe@saasovation.com";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS2 = "zdoe@saasovation.com";
    protected static final String FIXTURE_USERNAME = "jdoe";
    protected static final String FIXTURE_USERNAME2 = "zdoe";

    protected Tenant activeTenant;
    protected ApplicationContext applicationContext;
    protected EventStore eventStore;

    public ApplicationServiceTest() {
        super();
    }

    // 组聚合根
    protected Group group1Aggregate() {
        return this.tenantAggregate()
                   .provisionGroup(FIXTURE_GROUP_NAME + " 1", "A test group 1.");
    }

    // 另一个组聚合根
    protected Group group2Aggregate() {
        return this.tenantAggregate()
                   .provisionGroup(FIXTURE_GROUP_NAME + " 2", "A test group 2.");
    }

    // 角色聚合根
    protected Role roleAggregate() {
        return this.tenantAggregate()
                   .provisionRole(FIXTURE_ROLE_NAME, "A test role.", true);
    }

    // 租赁聚合根
    protected Tenant tenantAggregate() {
        if (activeTenant == null) {

            activeTenant =
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
        }

        return activeTenant;
    }

    // 用户聚合根
    protected User userAggregate() {

        Tenant tenant = this.tenantAggregate();

        RegistrationInvitation invitation =
                tenant.offerRegistrationInvitation("open-ended").openEnded();

        User user =
                tenant.registerUser(
                        invitation.invitationId(),
                        "jdoe",
                        FIXTURE_PASSWORD,
                        Enablement.indefiniteEnablement(),
                        new Person(
                                tenant.tenantId(),
                                new FullName("John", "Doe"),
                                new ContactInformation(
                                        new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS),
                                        new PostalAddress(
                                                "123 Pearl Street",
                                                "Boulder",
                                                "CO",
                                                "80301",
                                                "US"),
                                        new Telephone("303-555-1210"),
                                        new Telephone("303-555-1212"))));

        return user;
    }

    @Override
    protected void setUp() throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>>>> " + this.getName());

        super.setUp();

        DomainEventPublisher.instance().reset();

        applicationContext = new ClassPathXmlApplicationContext(
                        new String[] {
                        		"common/applicationContext-hibernate.xml",
                        		"common/applicationContext-common.xml",
                                "applicationContext-identityaccess-application.xml",
                                "applicationContext-identityaccess.xml"
                        });


        this.eventStore = (EventStore) applicationContext.getBean("eventStore");

        this.clean((CleanableStore) this.eventStore);
        this.clean((CleanableStore) DomainRegistry.groupRepository());
        this.clean((CleanableStore) DomainRegistry.roleRepository());
        this.clean((CleanableStore) DomainRegistry.tenantRepository());
        this.clean((CleanableStore) DomainRegistry.userRepository());
    }

    @Override
    protected void tearDown() throws Exception {
        this.clean((CleanableStore) this.eventStore);
        this.clean((CleanableStore) DomainRegistry.groupRepository());
        this.clean((CleanableStore) DomainRegistry.roleRepository());
        this.clean((CleanableStore) DomainRegistry.tenantRepository());
        this.clean((CleanableStore) DomainRegistry.userRepository());

        super.tearDown();

        System.out.println("<<<<<<<<<<<<<<<<<<<< (done)");
    }

    private void clean(CleanableStore aCleanableStore) {
        aCleanableStore.clean();
    }
}
