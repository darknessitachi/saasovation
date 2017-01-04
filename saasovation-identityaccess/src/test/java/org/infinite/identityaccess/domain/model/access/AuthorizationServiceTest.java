package org.infinite.identityaccess.domain.model.access;

import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 授权服务测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:30:57
 * @version V1.0
 */
public class AuthorizationServiceTest extends IdentityAccessTest {

    public AuthorizationServiceTest() {
        super();
    }

    // 测试用户拥有某角色授权
    public void testUserInRoleAuthorization() throws Exception {

        Tenant tenant = this.tenantAggregate();
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        Role managerRole = tenant.provisionRole("Manager", "A manager role.", true);

        managerRole.assignUser(user);

        DomainRegistry.roleRepository().add(managerRole);

        boolean authorized =
                DomainRegistry
                    .authorizationService()
                    .isUserInRole(user, "Manager");

        assertTrue(authorized);

        authorized =
                DomainRegistry
                    .authorizationService()
                    .isUserInRole(user, "Director");

        assertFalse(authorized);
    }

	// 测试用户名拥有某角色授权
    public void testUsernameInRoleAuthorization() throws Exception {

        Tenant tenant = this.tenantAggregate();
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        Role managerRole = tenant.provisionRole("Manager", "A manager role.", true);

        managerRole.assignUser(user);

        DomainRegistry.roleRepository().add(managerRole);

        boolean authorized =
                DomainRegistry
                    .authorizationService()
                    .isUserInRole(tenant.tenantId(), user.username(), "Manager");

        assertTrue(authorized);

        authorized =
                DomainRegistry
                    .authorizationService()
                    .isUserInRole(tenant.tenantId(), user.username(), "Director");

        assertFalse(authorized);
    }
}
