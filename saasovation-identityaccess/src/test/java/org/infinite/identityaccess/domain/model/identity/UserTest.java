package org.infinite.identityaccess.domain.model.identity;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.event.identity.PersonContactInformationChanged;
import org.infinite.identityaccess.domain.event.identity.PersonNameChanged;
import org.infinite.identityaccess.domain.event.identity.UserEnablementChanged;
import org.infinite.identityaccess.domain.event.identity.UserPasswordChanged;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.ContactInformation;
import org.infinite.identityaccess.domain.model.identity.EmailAddress;
import org.infinite.identityaccess.domain.model.identity.Enablement;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.PostalAddress;
import org.infinite.identityaccess.domain.model.identity.Telephone;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.model.identity.UserDescriptor;


/**
 * 用户测试
 * 
 * @author Darkness
 * @date 2014-5-27 下午8:04:22
 * @version V1.0
 */
public class UserTest extends IdentityAccessTest {

    private boolean handled;

    public UserTest() {
        super();
    }

    // 测试用户状态启用
    public void testUserEnablementEnabled() throws Exception {

        User user = this.userAggregate();

        assertTrue(user.isEnabled());
    }

	// 测试用户可用状态改成禁用
    public void testUserEnablementDisabled() throws Exception {

        final User user = this.userAggregate();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<UserEnablementChanged>() {
                public void handleEvent(UserEnablementChanged aDomainEvent) {
                    assertEquals(aDomainEvent.username(), user.username());
                    handled = true;
                }
                public Class<UserEnablementChanged> subscribedToEventType() {
                    return UserEnablementChanged.class;
                }
            });

        user.defineEnablement(new Enablement(false, null, null));

        assertFalse(user.isEnabled());
        assertTrue(handled);
    }

	// 测试用户可用状态改变起始日期+结束日期，且在租赁日期范围内
    public void testUserEnablementWithinStartEndDates() throws Exception {

        final User user = this.userAggregate();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<UserEnablementChanged>() {
                public void handleEvent(UserEnablementChanged aDomainEvent) {
                    assertEquals(aDomainEvent.username(), user.username());
                    handled = true;
                }
                public Class<UserEnablementChanged> subscribedToEventType() {
                    return UserEnablementChanged.class;
                }
            });

        user.defineEnablement(
                new Enablement(
                        true,
                        this.today(),
                        this.tomorrow()));

        assertTrue(user.isEnabled());
        assertTrue(handled);
    }

    // 测试用户可用状态改变起始日期+结束日期，且超出租赁日期范围
    public void testUserEnablementOutsideStartEndDates() throws Exception {

        final User user = this.userAggregate();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<UserEnablementChanged>() {
                public void handleEvent(UserEnablementChanged aDomainEvent) {
                    assertEquals(aDomainEvent.username(), user.username());
                    handled = true;
                }
                public Class<UserEnablementChanged> subscribedToEventType() {
                    return UserEnablementChanged.class;
                }
            });

        user.defineEnablement(
                new Enablement(
                        true,
                        this.dayBeforeYesterday(),
                        this.yesterday()));

        assertFalse(user.isEnabled());
        assertTrue(handled);
    }

    // 测试用户可用状态改变起始日期+结束日期，且起始日期晚于结束日期，应该抛出异常
    public void testUserEnablementUnsequencedDates() throws Exception {

        final User user = this.userAggregate();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<UserEnablementChanged>() {
                public void handleEvent(UserEnablementChanged aDomainEvent) {
                    assertEquals(aDomainEvent.username(), user.username());
                    handled = true;
                }
                public Class<UserEnablementChanged> subscribedToEventType() {
                    return UserEnablementChanged.class;
                }
            });

        boolean failure = false;

        try {
            user.defineEnablement(
                    new Enablement(
                            true,
                            this.tomorrow(),
                            this.today()));
        } catch (Throwable t) {
            failure = true;
        }

        assertTrue(failure);
        assertFalse(handled);
    }

    // 测试用户描述信息
    public void testUserDescriptor() throws Exception {

        User user = this.userAggregate();

        UserDescriptor userDescriptor = user.userDescriptor();

        assertNotNull(userDescriptor.emailAddress());
        assertEquals(userDescriptor.emailAddress(), FIXTURE_USER_EMAIL_ADDRESS);

        assertNotNull(userDescriptor.tenantId());
        assertEquals(userDescriptor.tenantId(), user.tenantId());

        assertNotNull(userDescriptor.username());
        assertEquals(userDescriptor.username(), FIXTURE_USERNAME);
    }

    // 测试用户密码改变
    public void testUserChangePassword() throws Exception {

        final User user = this.userAggregate();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<UserPasswordChanged>() {
                public void handleEvent(UserPasswordChanged aDomainEvent) {
                    assertEquals(aDomainEvent.username(), user.username());
                    assertEquals(aDomainEvent.tenantId(), user.tenantId());
                    handled = true;
                }
                public Class<UserPasswordChanged> subscribedToEventType() {
                    return UserPasswordChanged.class;
                }
            });

        user.changePassword(FIXTURE_PASSWORD, "ThisIsANewPassword.");

        assertTrue(handled);
    }

    // 测试用户原始密码不正确导致改变密码失败
    public void testUserChangePasswordFails() throws Exception {

        User user = this.userAggregate();

        try {

            user.changePassword("no clue", "ThisIsANewP4ssw0rd.");

            assertEquals(FIXTURE_PASSWORD, "no clue");

        } catch (Exception e) {
            // good path, fall through
        }
    }

    // 测试用户密码设置正确
    public void testUserPasswordHashedOnConstruction() throws Exception {

        User user = this.userAggregate();

        assertFalse(FIXTURE_PASSWORD.equals(user.password()));
    }

	// 测试用户密码修改成功
    public void testUserPasswordHashedOnChange() throws Exception {

        User user = this.userAggregate();

        String strongPassword = DomainRegistry.passwordService().generateStrongPassword();

        user.changePassword(FIXTURE_PASSWORD, strongPassword);

        // 修改密码成功，这个好像不对，就算修改密码失败，此处依然测试通过，因为原始密码与加密密码永远不会相等
        assertFalse(FIXTURE_PASSWORD.equals(user.password()));
        // 密码加密成功
        assertFalse(strongPassword.equals(user.password()));
    }

    // 测试修改用户个人联系信息成功
    public void testUserPersonalContactInformationChanged() throws Exception {

        final User user = this.userAggregate();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<PersonContactInformationChanged>() {
                public void handleEvent(PersonContactInformationChanged aDomainEvent) {
                    assertEquals(aDomainEvent.username(), user.username());
                    handled = true;
                }
                public Class<PersonContactInformationChanged> subscribedToEventType() {
                    return PersonContactInformationChanged.class;
                }
            });

        user.changePersonalContactInformation(
                new ContactInformation(
                    new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS2),
                    new PostalAddress(
                            "123 Mockingbird Lane",
                            "Boulder",
                            "CO",
                            "80301",
                            "US"),
                    new Telephone("303-555-1210"),
                    new Telephone("303-555-1212")));

        assertEquals(new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS2), user.person().emailAddress());
        assertEquals("123 Mockingbird Lane", user.person().contactInformation().postalAddress().streetAddress());
        assertTrue(handled);
    }

    // 测试修改用户个人名字
    public void testUserPersonNameChanged() throws Exception {

        final User user = this.userAggregate();

        DomainEventPublisher
            .instance()
            .subscribe(new DomainEventSubscriber<PersonNameChanged>() {
                public void handleEvent(PersonNameChanged aDomainEvent) {
                    assertEquals(aDomainEvent.username(), user.username());
                    assertEquals(aDomainEvent.name().firstName(), "Joe");
                    assertEquals(aDomainEvent.name().lastName(), "Smith");
                    handled = true;
                }
                public Class<PersonNameChanged> subscribedToEventType() {
                    return PersonNameChanged.class;
                }
            });

        user.changePersonalName(new FullName("Joe", "Smith"));

        assertTrue(handled);
    }
}
