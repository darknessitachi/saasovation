package org.infinite.identityaccess.application;

import org.infinite.identityaccess.application.ApplicationServiceRegistry;
import org.infinite.identityaccess.application.command.AssignUserToRoleCommand;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 授权应用服务测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:36:23
 * @version V1.0
 */
public class AccessApplicationServiceTest extends ApplicationServiceTest {

    public AccessApplicationServiceTest() {
        super();
    }

    // 测试授权角色给用户
    public void testAssignUserToRole() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        Role role = this.roleAggregate();
        DomainRegistry.roleRepository().add(role);

        assertFalse(role.isInRole(user, DomainRegistry.groupMemberService()));

        ApplicationServiceRegistry
            .accessApplicationService()
            .assignUserToRole(
                    new AssignUserToRoleCommand(
                            user.tenantId().id(),
                            user.username(),
                            role.name()));

        assertTrue(role.isInRole(user, DomainRegistry.groupMemberService()));
    }

	// 测试用户拥有某角色
    public void testIsUserInRole() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        Role role = this.roleAggregate();
        DomainRegistry.roleRepository().add(role);

        assertFalse(
                ApplicationServiceRegistry
                    .accessApplicationService()
                    .isUserInRole(
                            user.tenantId().id(),
                            user.username(),
                            role.name()));

        ApplicationServiceRegistry
            .accessApplicationService()
            .assignUserToRole(
                    new AssignUserToRoleCommand(
                            user.tenantId().id(),
                            user.username(),
                            role.name()));

        assertTrue(
                ApplicationServiceRegistry
                    .accessApplicationService()
                    .isUserInRole(
                            user.tenantId().id(),
                            user.username(),
                            role.name()));
    }

	// 测试用户拥有某角色
    public void testUserInRole() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        Role role = this.roleAggregate();
        DomainRegistry.roleRepository().add(role);

        User userNotInRole =
                ApplicationServiceRegistry
                    .accessApplicationService()
                    .userInRole(user.tenantId().id(), user.username(), role.name());

        assertNull(userNotInRole);

        ApplicationServiceRegistry
            .accessApplicationService()
            .assignUserToRole(
                    new AssignUserToRoleCommand(
                            user.tenantId().id(),
                            user.username(),
                            role.name()));

        User userInRole =
                ApplicationServiceRegistry
                    .accessApplicationService()
                    .userInRole(user.tenantId().id(), user.username(), role.name());

        assertNotNull(userInRole);
    }
}
