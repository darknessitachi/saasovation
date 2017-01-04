package org.infinite.identityaccess.domain.model.identity;

import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.model.identity.UserDescriptor;


/**
 * 授权服务测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:01:47
 * @version V1.0
 */
public class AuthenticationServiceTest extends IdentityAccessTest {

    public AuthenticationServiceTest() {
        super();
    }

    // 测试授权成功
    public void testAuthenticationSuccess() throws Exception {

        User user = this.userAggregate();

        DomainRegistry.userRepository().add(user);

        UserDescriptor userDescriptor =
            DomainRegistry
                .authenticationService()
                .authenticate(
                        user.tenantId(),
                        user.username(),
                        FIXTURE_PASSWORD);

        assertNotNull(userDescriptor);
        assertFalse(userDescriptor.isNullDescriptor());
        assertEquals(userDescriptor.tenantId(), user.tenantId());
        assertEquals(userDescriptor.username(), user.username());
        assertEquals(userDescriptor.emailAddress(), user.person().emailAddress().address());
    }

    // 测试授权租赁不正确
    public void testAuthenticationTenantFailure() throws Exception {

        User user = this.userAggregate();

        DomainRegistry
            .userRepository()
            .add(user);

        UserDescriptor userDescriptor =
            DomainRegistry
                .authenticationService()
                .authenticate(
                        DomainRegistry.tenantRepository().nextIdentity(),
                        user.username(),
                        FIXTURE_PASSWORD);

        assertNotNull(userDescriptor);
        assertTrue(userDescriptor.isNullDescriptor());
    }

    // 测试授权用户名不正确
    public void testAuthenticationUsernameFailure() throws Exception {

        User user = this.userAggregate();

        DomainRegistry
            .userRepository()
            .add(user);

        UserDescriptor userDescriptor =
            DomainRegistry
                .authenticationService()
                .authenticate(
                        user.tenantId(),
                        FIXTURE_USERNAME2,
                        user.password());

        assertNotNull(userDescriptor);
        assertTrue(userDescriptor.isNullDescriptor());
    }

    // 测试授权密码不正确
    public void testAuthenticationPasswordFailure() throws Exception {

        User user = this.userAggregate();

        DomainRegistry
            .userRepository()
            .add(user);

        UserDescriptor userDescriptor =
            DomainRegistry
                .authenticationService()
                .authenticate(
                        user.tenantId(),
                        user.username(),
                        FIXTURE_PASSWORD + "-");

        assertNotNull(userDescriptor);
        assertTrue(userDescriptor.isNullDescriptor());
    }
}
