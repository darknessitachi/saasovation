package org.infinite.identityaccess.application;

import javax.annotation.Resource;

import org.infinite.identityaccess.application.command.ActivateTenantCommand;
import org.infinite.identityaccess.application.command.AddGroupToGroupCommand;
import org.infinite.identityaccess.application.command.AddUserToGroupCommand;
import org.infinite.identityaccess.application.command.AuthenticateUserCommand;
import org.infinite.identityaccess.application.command.ChangeContactInfoCommand;
import org.infinite.identityaccess.application.command.ChangeEmailAddressCommand;
import org.infinite.identityaccess.application.command.ChangePostalAddressCommand;
import org.infinite.identityaccess.application.command.ChangePrimaryTelephoneCommand;
import org.infinite.identityaccess.application.command.ChangeSecondaryTelephoneCommand;
import org.infinite.identityaccess.application.command.ChangeUserPasswordCommand;
import org.infinite.identityaccess.application.command.ChangeUserPersonalNameCommand;
import org.infinite.identityaccess.application.command.DeactivateTenantCommand;
import org.infinite.identityaccess.application.command.DefineUserEnablementCommand;
import org.infinite.identityaccess.application.command.ProvisionGroupCommand;
import org.infinite.identityaccess.application.command.ProvisionTenantCommand;
import org.infinite.identityaccess.application.command.RegisterUserCommand;
import org.infinite.identityaccess.application.command.RemoveGroupFromGroupCommand;
import org.infinite.identityaccess.application.command.RemoveUserFromGroupCommand;
import org.infinite.identityaccess.domain.model.identity.ContactInformation;
import org.infinite.identityaccess.domain.model.identity.EmailAddress;
import org.infinite.identityaccess.domain.model.identity.Enablement;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.Person;
import org.infinite.identityaccess.domain.model.identity.PostalAddress;
import org.infinite.identityaccess.domain.model.identity.Telephone;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.model.identity.UserDescriptor;
import org.infinite.identityaccess.domain.repository.GroupRepository;
import org.infinite.identityaccess.domain.repository.TenantRepository;
import org.infinite.identityaccess.domain.repository.UserRepository;
import org.infinite.identityaccess.domain.service.AuthenticationService;
import org.infinite.identityaccess.domain.service.GroupMemberService;
import org.infinite.identityaccess.domain.service.TenantProvisioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 身份应用服务
 * 
 * @author Darkness
 * @date 2014-5-28 下午8:09:37
 * @version V1.0
 */
@Transactional
public class IdentityApplicationService {

    public IdentityApplicationService() {
        super();

        // IdentityAccessEventProcessor.register();
    }
    
    //[start]租赁
    // 准备租赁，会创建管理员账号跟管理员角色
    @Transactional
    public Tenant provisionTenant(ProvisionTenantCommand aCommand) {

        return
            this.tenantProvisioningService().provisionTenant(
                        aCommand.getTenantName(),
                        aCommand.getTenantDescription(),
                        new FullName(
                                aCommand.getAdministorFirstName(),
                                aCommand.getAdministorLastName()),
                        new EmailAddress(aCommand.getEmailAddress()),
                        new PostalAddress(
                                aCommand.getAddressStateProvince(),
                                aCommand.getAddressCity(),
                                aCommand.getAddressStateProvince(),
                                aCommand.getAddressPostalCode(),
                                aCommand.getAddressCountryCode()),
                        new Telephone(aCommand.getPrimaryTelephone()),
                        new Telephone(aCommand.getSecondaryTelephone()));
    }
    
    // 激活租赁
    @Transactional
    public void activateTenant(ActivateTenantCommand aCommand) {
        Tenant tenant = this.existingTenant(aCommand.getTenantId());

        tenant.activate();
    }

    // 禁用租赁
    @Transactional
    public void deactivateTenant(DeactivateTenantCommand aCommand) {
        Tenant tenant = this.existingTenant(aCommand.getTenantId());

        tenant.deactivate();
    }
    //[end]租赁
    
    //[start]用户
    @Transactional
    public User registerUser(RegisterUserCommand aCommand) {
        Tenant tenant = this.existingTenant(aCommand.getTenantId());

        User user =
            tenant.registerUser(
                    aCommand.getInvitationIdentifier(),
                    aCommand.getUsername(),
                    aCommand.getPassword(),
                    new Enablement(
                            aCommand.isEnabled(),
                            aCommand.getStartDate(),
                            aCommand.getEndDate()),
                    new Person(
                            new TenantId(aCommand.getTenantId()),
                            new FullName(aCommand.getFirstName(), aCommand.getLastName()),
                            new ContactInformation(
                                    new EmailAddress(aCommand.getEmailAddress()),
                                    new PostalAddress(
                                            aCommand.getAddressStateProvince(),
                                            aCommand.getAddressCity(),
                                            aCommand.getAddressStateProvince(),
                                            aCommand.getAddressPostalCode(),
                                            aCommand.getAddressCountryCode()),
                                    new Telephone(aCommand.getPrimaryTelephone()),
                                    new Telephone(aCommand.getSecondaryTelephone()))));

        if (user == null) {
            throw new IllegalStateException("User not registered.");
        }

        this.userRepository().add(user);

        return user;
    }
    
    // 验证用户
    @Transactional(readOnly=true)
    public UserDescriptor authenticateUser(AuthenticateUserCommand aCommand) {
        UserDescriptor userDescriptor =
                this.authenticationService()
                    .authenticate(
                        new TenantId(aCommand.getTenantId()),
                        aCommand.getUsername(),
                        aCommand.getPassword());

        return userDescriptor;
    }
	
    // 改变用户联系人信息
    @Transactional
    public void changeUserContactInformation(ChangeContactInfoCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        this.internalChangeUserContactInformation(
                user,
                new ContactInformation(
                        new EmailAddress(aCommand.getEmailAddress()),
                        new PostalAddress(
                                aCommand.getAddressStreetAddress(),
                                aCommand.getAddressCity(),
                                aCommand.getAddressStateProvince(),
                                aCommand.getAddressPostalCode(),
                                aCommand.getAddressCountryCode()),
                        new Telephone(aCommand.getPrimaryTelephone()),
                        new Telephone(aCommand.getSecondaryTelephone())));
    }

    // 改变用户Email
    @Transactional
    public void changeUserEmailAddress(ChangeEmailAddressCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        this.internalChangeUserContactInformation(
                user,
                user.person()
                    .contactInformation()
                    .changeEmailAddress(new EmailAddress(aCommand.getEmailAddress())));
    }

    // 改变用户邮政地址
    @Transactional
    public void changeUserPostalAddress(ChangePostalAddressCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        this.internalChangeUserContactInformation(
                user,
                user.person()
                    .contactInformation()
                    .changePostalAddress(
                            new PostalAddress(
                                    aCommand.getAddressStreetAddress(),
                                    aCommand.getAddressCity(),
                                    aCommand.getAddressStateProvince(),
                                    aCommand.getAddressPostalCode(),
                                    aCommand.getAddressCountryCode())));
    }

    // 改变用户主号码
    @Transactional
    public void changeUserPrimaryTelephone(ChangePrimaryTelephoneCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        this.internalChangeUserContactInformation(
                user,
                user.person()
                    .contactInformation()
                    .changePrimaryTelephone(new Telephone(aCommand.getTelephone())));
    }

    // 改变用户副号码
    @Transactional
    public void changeUserSecondaryTelephone(ChangeSecondaryTelephoneCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        this.internalChangeUserContactInformation(
                user,
                user.person()
                    .contactInformation()
                    .changeSecondaryTelephone(new Telephone(aCommand.getTelephone())));
    }
    
    private void internalChangeUserContactInformation(User aUser, ContactInformation aContactInformation) {

        if (aUser == null) {
            throw new IllegalArgumentException("User must exist.");
        }

        aUser.person().changeContactInformation(aContactInformation);
    }

    // 改变用户密码
    @Transactional
    public void changeUserPassword(ChangeUserPasswordCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        user.changePassword(aCommand.getCurrentPassword(), aCommand.getChangedPassword());
    }

    // 改变用户个人姓名
    @Transactional
    public void changeUserPersonalName(ChangeUserPersonalNameCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        user.person().changeName(new FullName(aCommand.getFirstName(), aCommand.getLastName()));
    }

    // 设定用户可用状态
    @Transactional
    public void defineUserEnablement(DefineUserEnablementCommand aCommand) {
        User user = this.existingUser(aCommand.getTenantId(), aCommand.getUsername());

        user.defineEnablement(
                new Enablement(
                        aCommand.isEnabled(),
                        aCommand.getStartDate(),
                        aCommand.getEndDate()));
    }
    //[end]用户
    
    //[start]组
	
    @Transactional
    public Group provisionGroup(ProvisionGroupCommand aCommand) {
        Tenant tenant = this.existingTenant(aCommand.getTenantId());

        Group group =
                tenant.provisionGroup(
                        aCommand.getGroupName(),
                        aCommand.getDescription());

        this.groupRepository().add(group);

        return group;
    }
    
    // 组添加组成员
    @Transactional
    public void addGroupToGroup(AddGroupToGroupCommand aCommand) {
        Group parentGroup =
                this.existingGroup(
                        aCommand.getTenantId(),
                        aCommand.getParentGroupName());

        Group childGroup =
                this.existingGroup(
                        aCommand.getTenantId(),
                        aCommand.getChildGroupName());

        parentGroup.addGroup(childGroup, this.groupMemberService());
    }

    // 组添加用户成员
    @Transactional
    public void addUserToGroup(AddUserToGroupCommand aCommand) {
        Group group =
                this.existingGroup(
                        aCommand.getTenantId(),
                        aCommand.getGroupName());

        User user =
                this.existingUser(
                        aCommand.getTenantId(),
                        aCommand.getUsername());

        group.addUser(user);
    }
    
    @Transactional(readOnly=true)
    public boolean isGroupMember(String aTenantId, String aGroupName, String aUsername) {
        Group group =
                this.existingGroup(
                        aTenantId,
                        aGroupName);

        User user =
                this.existingUser(
                        aTenantId,
                        aUsername);

        return group.isMember(user, this.groupMemberService());
    }

	// 删除组成员
    @Transactional
    public void removeGroupFromGroup(RemoveGroupFromGroupCommand aCommand) {
        Group parentGroup =
                this.existingGroup(
                        aCommand.getTenantId(),
                        aCommand.getParentGroupName());

        Group childGroup =
                this.existingGroup(
                        aCommand.getTenantId(),
                        aCommand.getChildGroupName());

        parentGroup.removeGroup(childGroup);
    }

    // 删除组用户成员
    @Transactional
    public void removeUserFromGroup(RemoveUserFromGroupCommand aCommand) {
        Group group =
                this.existingGroup(
                        aCommand.getTenantId(),
                        aCommand.getGroupName());

        User user =
                this.existingUser(
                        aCommand.getTenantId(),
                        aCommand.getUsername());

        group.removeUser(user);
    }
	//[end]组

    //[start]查询
    // 查询租赁
    @Transactional(readOnly=true)
    public Tenant tenant(String aTenantId) {
        Tenant tenant =
                this.tenantRepository()
                    .tenantOfId(new TenantId(aTenantId));

        return tenant;
    }
    
	// 查询组
    @Transactional(readOnly=true)
    public Group group(String aTenantId, String aGroupName) {
        Group group =
                this.groupRepository()
                    .groupNamed(new TenantId(aTenantId), aGroupName);

        return group;
    }

    // 查询用户
    @Transactional(readOnly=true)
    public User user(String aTenantId, String aUsername) {
        User user =
                this.userRepository()
                    .userWithUsername(
                            new TenantId(aTenantId),
                            aUsername);

        return user;
    }

    // 查询用户描述
    @Transactional(readOnly=true)
    public UserDescriptor userDescriptor(
            String aTenantId,
            String aUsername) {

        UserDescriptor userDescriptor = null;

        User user = this.user(aTenantId, aUsername);

        if (user != null) {
            userDescriptor = user.userDescriptor();
        }

        return userDescriptor;
    }

    // 查询存在的组
    private Group existingGroup(String aTenantId, String aGroupName) {
        Group group = this.group(aTenantId, aGroupName);

        if (group == null) {
            throw new IllegalArgumentException(
                    "Group does not exist for: "
                    + aTenantId + " and: " + aGroupName);
        }

        return group;
    }

    // 查询存在的租赁
    private Tenant existingTenant(String aTenantId) {
        Tenant tenant = this.tenant(aTenantId);

        if (tenant == null) {
            throw new IllegalArgumentException(
                    "Tenant does not exist for: " + aTenantId);
        }

        return tenant;
    }

    // 查询存在的用户
    private User existingUser(String aTenantId, String aUsername) {
        User user = this.user(aTenantId, aUsername);

        if (user == null) {
            throw new IllegalArgumentException(
                    "User does not exist for: "
                    + aTenantId + " and " + aUsername);
        }

        return user;
    }
 	//[end]查询
    
    //[start]仓储&服务
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GroupMemberService groupMemberService;
    
    @Autowired
    private TenantProvisioningService tenantProvisioningService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;
    
    private AuthenticationService authenticationService() {
        return this.authenticationService;
    }
    
    private GroupMemberService groupMemberService() {
        return this.groupMemberService;
    }

    private TenantProvisioningService tenantProvisioningService() {
        return this.tenantProvisioningService;
    }

    private TenantRepository tenantRepository() {
        return this.tenantRepository;
    }

    private UserRepository userRepository() {
        return this.userRepository;
    }

    private GroupRepository groupRepository() {
        return this.groupRepository;
    }
    //[end]仓储&服务
}
