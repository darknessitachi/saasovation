package org.infinite.identityaccess.domain.model;

import java.util.Date;

import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.identity.ContactInformation;
import org.infinite.identityaccess.domain.model.identity.EmailAddress;
import org.infinite.identityaccess.domain.model.identity.Enablement;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.Person;
import org.infinite.identityaccess.domain.model.identity.PostalAddress;
import org.infinite.identityaccess.domain.model.identity.RegistrationInvitation;
import org.infinite.identityaccess.domain.model.identity.Telephone;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 身份认证测试基类
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:55:05
 * @version V1.0
 */
public abstract class IdentityAccessTest extends DomainTest {

	/**
	 * 测试用的常量
	 */
    protected static final String FIXTURE_PASSWORD = "SecretPassword!";
    protected static final String FIXTURE_TENANT_NAME = "Test Tenant";
    protected static final String FIXTURE_TENANT_DESCRIPTION = "This is a test tenant.";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS = "darkness@saasovation.com";
    protected static final String FIXTURE_USER_EMAIL_ADDRESS2 = "sky@saasovation.com";
    protected static final String FIXTURE_USERNAME = "jdoe";
    protected static final String FIXTURE_USERNAME2 = "zdoe";
    protected static final long TWENTY_FOUR_HOURS = (1000L * 60L * 60L * 24L);

    private Tenant tenant;

    public IdentityAccessTest() {
        super();
    }

    // 联系信息
    protected ContactInformation contactInformation() {
        return
            new ContactInformation(
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

    // 人员1
    protected Person personEntity(Tenant aTenant) {

        Person person =
            new Person(
                    aTenant.tenantId(),
                    new FullName("John", "Doe"),
                    this.contactInformation());

        return person;
    }

    // 人员2
    protected Person personEntity2(Tenant aTenant) {

        Person person =
            new Person(
                    aTenant.tenantId(),
                    new FullName("Zoe", "Doe"),
                    new ContactInformation(
                            new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS2),
                            new PostalAddress(
                                    "123 Pearl Street",
                                    "Boulder",
                                    "CO",
                                    "80301",
                                    "US"),
                            new Telephone("303-555-1210"),
                            new Telephone("303-555-1212")));

        return person;
    }

    // 提供一个今天到明天的注册邀请
    protected RegistrationInvitation registrationInvitationEntity(Tenant aTenant) throws Exception {

        Date today = new Date();

        Date tomorrow = new Date(today.getTime() + TWENTY_FOUR_HOURS);

        // 提供一个今天到明天的注册邀请
        RegistrationInvitation registrationInvitation =
            aTenant.offerRegistrationInvitation("Today-and-Tomorrow: " + System.nanoTime())
            .startingOn(today)
            .until(tomorrow);

        return registrationInvitation;
    }

    // 租赁聚合根
    protected Tenant tenantAggregate() {

        if (this.tenant == null) {
            TenantId tenantId = DomainRegistry.tenantRepository().nextIdentity();

            this.tenant =
                new Tenant(
                        tenantId,
                        FIXTURE_TENANT_NAME,
                        FIXTURE_TENANT_DESCRIPTION,
                        true);

            DomainRegistry.tenantRepository().add(tenant);
        }

        return this.tenant;
    }

    // 前天的日期
    protected Date dayBeforeYesterday() {
        return new Date(this.today().getTime() - (TWENTY_FOUR_HOURS * 2));
    }
    
    // 昨天
    protected Date yesterday() {
        return new Date(this.today().getTime() - TWENTY_FOUR_HOURS);
    }
    
    // 今天
    protected Date today() {
        return new Date();
    }

    // 明天
    protected Date tomorrow() {
        return new Date(this.today().getTime() + TWENTY_FOUR_HOURS);
    }
    
    // 后天的日期
    protected Date dayAfterTomorrow() {
        return new Date(this.today().getTime() + (TWENTY_FOUR_HOURS * 2));
    }

    // 用户聚合根，可用状态未设置起始日期、结束日期
    protected User userAggregate() throws Exception {
        Tenant tenant = this.tenantAggregate();

        RegistrationInvitation registrationInvitation = this.registrationInvitationEntity(tenant);

        User user =
            tenant.registerUser(
                    registrationInvitation.invitationId(),
                    FIXTURE_USERNAME,
                    FIXTURE_PASSWORD,
                    new Enablement(true, null, null),
                    this.personEntity(tenant));

        return user;
    }

    // 另一个用户聚合根，可用状态未设置起始日期、结束日期
    protected User userAggregate2() throws Exception {
        Tenant tenant = this.tenantAggregate();

        RegistrationInvitation registrationInvitation = this.registrationInvitationEntity(tenant);

        User user =
            tenant.registerUser(
                    registrationInvitation.invitationId(),
                    FIXTURE_USERNAME2,
                    FIXTURE_PASSWORD,
                    new Enablement(true, null, null),
                    this.personEntity2(tenant));

        return user;
    }

}
