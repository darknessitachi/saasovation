package org.infinite.identityaccess.domain.service;

import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.repository.GroupRepository;
import org.infinite.identityaccess.domain.repository.RoleRepository;
import org.infinite.identityaccess.domain.repository.UserRepository;

import com.rapidark.framework.commons.lang.AssertionConcern;


/**
 * 授权服务
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:12:43
 * @version V1.0
 */
public class AuthorizationService extends AssertionConcern {

    private GroupRepository groupRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public AuthorizationService(
            UserRepository aUserRepository,
            GroupRepository aGroupRepository,
            RoleRepository aRoleRepository) {

        super();

        this.groupRepository = aGroupRepository;
        this.roleRepository = aRoleRepository;
        this.userRepository = aUserRepository;
    }

    public boolean isUserInRole(TenantId aTenantId, String aUsername, String aRoleName) {
        this.assertArgumentNotNull(aTenantId, "TenantId must not be null.");
        this.assertArgumentNotEmpty(aUsername, "Username must not be provided.");
        this.assertArgumentNotEmpty(aRoleName, "Role name must not be null.");

        User user = this.userRepository().userWithUsername(aTenantId, aUsername);

        return user == null ? false : this.isUserInRole(user, aRoleName);
    }

    public boolean isUserInRole(User aUser, String aRoleName) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentNotEmpty(aRoleName, "Role name must not be null.");

        boolean authorized = false;

        if (aUser.isEnabled()) {
            Role role = this.roleRepository().roleNamed(aUser.tenantId(), aRoleName);

            if (role != null) {
                GroupMemberService groupMemberService =
                        new GroupMemberService(
                                this.userRepository(),
                                this.groupRepository());

                authorized = role.isInRole(aUser, groupMemberService);
            }
        }

        return authorized;
    }

    private GroupRepository groupRepository() {
        return this.groupRepository;
    }

    private RoleRepository roleRepository() {
        return this.roleRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }
}
