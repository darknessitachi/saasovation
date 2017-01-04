package org.infinite.identityaccess.application;

import java.util.Date;

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
import org.infinite.identityaccess.application.command.ProvisionTenantCommand;
import org.infinite.identityaccess.application.command.RemoveGroupFromGroupCommand;
import org.infinite.identityaccess.application.command.RemoveUserFromGroupCommand;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.model.identity.UserDescriptor;


/**
 * 身份应用服务测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午8:08:10
 * @version V1.0
 */
public class IdentityApplicationServiceTest extends ApplicationServiceTest {

    public IdentityApplicationServiceTest() {
        super();
    }
    
    // 测试准备租赁
    public void testProvisionTenant() {
    	ProvisionTenantCommand aCommand = new ProvisionTenantCommand();
    	aCommand.setTenantName(FIXTURE_TENANT_NAME);
    	aCommand.setTenantDescription(FIXTURE_TENANT_DESCRIPTION);
    	aCommand.setAdministorFirstName("Darkness");
    	aCommand.setAdministorLastName("Sky");
    	aCommand.setEmailAddress(FIXTURE_USER_EMAIL_ADDRESS);
    	aCommand.setPrimaryTelephone("303-555-1210");
    	aCommand.setSecondaryTelephone("303-555-1212");
    	aCommand.setAddressStreetAddress("123 Pearl Street");
    	aCommand.setAddressCity( "Boulder");
    	aCommand.setAddressStateProvince("CO");
    	aCommand.setAddressPostalCode("80301");
    	aCommand.setAddressCountryCode("US");
    	
    	Tenant tenant = ApplicationServiceRegistry.identityApplicationService().provisionTenant(aCommand);
    	
    	assertNotNull(tenant);
        assertEquals(tenant.name(), FIXTURE_TENANT_NAME);
        assertTrue(tenant.isActive());
    }

//    // 测试激活租赁
//    public void testActivateTenant() throws Exception {
//        Tenant tenant = this.tenantAggregate();
//        
//        tenant.deactivate();
//        assertFalse(tenant.isActive());
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .activateTenant(new ActivateTenantCommand(tenant.tenantId().id()));
//
//        Tenant changedTenant = DomainRegistry.tenantRepository().tenantOfId(tenant.tenantId());
//
//        assertNotNull(changedTenant);
//        assertEquals(tenant.name(), changedTenant.name());
//        assertTrue(changedTenant.isActive());
//    }
//    
//    // 测试禁用租赁
//    public void testDeactivateTenant() throws Exception {
//        Tenant tenant = this.tenantAggregate();
//        assertTrue(tenant.isActive());
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .deactivateTenant(new DeactivateTenantCommand(tenant.tenantId().id()));
//
//        Tenant changedTenant = DomainRegistry.tenantRepository().tenantOfId(tenant.tenantId());
//
//        assertNotNull(changedTenant);
//        assertEquals(tenant.name(), changedTenant.name());
//        assertFalse(changedTenant.isActive());
//    }
//
//    // 测试组添加组成员
//    public void testAddGroupToGroup() throws Exception {
//        Group parentGroup = this.group1Aggregate();
//        DomainRegistry.groupRepository().add(parentGroup);
//
//        Group childGroup = this.group2Aggregate();
//        DomainRegistry.groupRepository().add(childGroup);
//
//        assertEquals(0, parentGroup.groupMembers().size());
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .addGroupToGroup(new AddGroupToGroupCommand(
//                    parentGroup.tenantId().id(),
//                    parentGroup.name(),
//                    childGroup.name()));
//
//        assertEquals(1, parentGroup.groupMembers().size());
//    }
//
//	// 测试组添加用户成员
//    public void testAddUserToGroup() throws Exception {
//        Group parentGroup = this.group1Aggregate();
//        DomainRegistry.groupRepository().add(parentGroup);
//
//        Group childGroup = this.group2Aggregate();
//        DomainRegistry.groupRepository().add(childGroup);
//
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        assertEquals(0, parentGroup.groupMembers().size());
//        assertEquals(0, childGroup.groupMembers().size());
//
//        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .addUserToGroup(new AddUserToGroupCommand(
//                childGroup.tenantId().id(),
//                childGroup.name(),
//                user.username()));
//
//        assertEquals(1, parentGroup.groupMembers().size());
//        assertEquals(1, childGroup.groupMembers().size());
//        assertTrue(parentGroup.isMember(user, DomainRegistry.groupMemberService()));
//        assertTrue(childGroup.isMember(user, DomainRegistry.groupMemberService()));
//    }
//
//    // 测试验证用户
//    public void testAuthenticateUser() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        UserDescriptor userDescriptor =
//                ApplicationServiceRegistry
//                    .identityApplicationService()
//                    .authenticateUser(new AuthenticateUserCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            FIXTURE_PASSWORD));
//
//        assertNotNull(userDescriptor);
//        assertEquals(user.username(), userDescriptor.username());
//    }
//
//    // 测试改变用户联系人信息
//    public void testChangeUserContactInformation() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .changeUserContactInformation(
//                    new ChangeContactInfoCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            "mynewemailaddress@saasovation.com",
//                            "777-555-1211",
//                            "777-555-1212",
//                            "123 Pine Street",
//                            "Loveland",
//                            "CO",
//                            "80771",
//                            "US"));
//
//        User changedUser =
//                DomainRegistry
//                    .userRepository()
//                    .userWithUsername(
//                            user.tenantId(),
//                            user.username());
//
//        assertNotNull(changedUser);
//        assertEquals("mynewemailaddress@saasovation.com", changedUser.person().emailAddress().address());
//        assertEquals("777-555-1211", changedUser.person().contactInformation().primaryTelephone().number());
//        assertEquals("777-555-1212", changedUser.person().contactInformation().secondaryTelephone().number());
//        assertEquals("123 Pine Street", changedUser.person().contactInformation().postalAddress().streetAddress());
//        assertEquals("Loveland", changedUser.person().contactInformation().postalAddress().city());
//    }
//
//    // 测试改变用户Email
//    public void testChangeUserEmailAddress() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .changeUserEmailAddress(
//                    new ChangeEmailAddressCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            "mynewemailaddress@saasovation.com"));
//
//        User changedUser =
//                DomainRegistry
//                    .userRepository()
//                    .userWithUsername(
//                            user.tenantId(),
//                            user.username());
//
//        assertNotNull(changedUser);
//        assertEquals("mynewemailaddress@saasovation.com", changedUser.person().emailAddress().address());
//    }
//
//    // 测试改变用户邮政地址
//    public void testChangeUserPostalAddress() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .changeUserPostalAddress(
//                    new ChangePostalAddressCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            "123 Pine Street",
//                            "Loveland",
//                            "CO",
//                            "80771",
//                            "US"));
//
//        User changedUser =
//                DomainRegistry
//                    .userRepository()
//                    .userWithUsername(
//                            user.tenantId(),
//                            user.username());
//
//        assertNotNull(changedUser);
//        assertEquals("123 Pine Street", changedUser.person().contactInformation().postalAddress().streetAddress());
//        assertEquals("Loveland", changedUser.person().contactInformation().postalAddress().city());
//    }
//
//    // 测试改变用户主号码
//    public void testChangeUserPrimaryTelephone() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .changeUserPrimaryTelephone(
//                    new ChangePrimaryTelephoneCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            "777-555-1211"));
//
//        User changedUser =
//                DomainRegistry
//                    .userRepository()
//                    .userWithUsername(
//                            user.tenantId(),
//                            user.username());
//
//        assertNotNull(changedUser);
//        assertEquals("777-555-1211", changedUser.person().contactInformation().primaryTelephone().number());
//    }
//
//    // 测试改变用户副号码
//    public void testChangeUserSecondaryTelephone() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .changeUserSecondaryTelephone(
//                    new ChangeSecondaryTelephoneCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            "777-555-1212"));
//
//        User changedUser =
//                DomainRegistry
//                    .userRepository()
//                    .userWithUsername(
//                            user.tenantId(),
//                            user.username());
//
//        assertNotNull(changedUser);
//        assertEquals("777-555-1212", changedUser.person().contactInformation().secondaryTelephone().number());
//    }
//
//    // 测试改变用户密码
//    public void testChangeUserPassword() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .changeUserPassword(
//                    new ChangeUserPasswordCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            FIXTURE_PASSWORD,
//                            "THIS.IS.JOE'S.NEW.PASSWORD"));
//
//        UserDescriptor userDescriptor =
//                ApplicationServiceRegistry
//                    .identityApplicationService()
//                    .authenticateUser(new AuthenticateUserCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            "THIS.IS.JOE'S.NEW.PASSWORD"));
//
//        assertNotNull(userDescriptor);
//        assertEquals(user.username(), userDescriptor.username());
//    }
//
//    // 测试改变用户个人姓名
//    public void testChangeUserPersonalName() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .changeUserPersonalName(
//                    new ChangeUserPersonalNameCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            "World",
//                            "Peace"));
//
//        User changedUser =
//                DomainRegistry
//                    .userRepository()
//                    .userWithUsername(
//                            user.tenantId(),
//                            user.username());
//
//        assertNotNull(changedUser);
//        assertEquals("World Peace", changedUser.person().name().asFormattedName());
//    }
//
//    // 测试设定用户可用状态
//    public void testDefineUserEnablement() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        Date now = new Date();
//        Date then = new Date(now.getTime() + (60 * 60 * 24 * 365 * 1000));
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .defineUserEnablement(
//                    new DefineUserEnablementCommand(
//                            user.tenantId().id(),
//                            user.username(),
//                            true,
//                            now,
//                            then));
//
//        User changedUser =
//                DomainRegistry
//                    .userRepository()
//                    .userWithUsername(
//                            user.tenantId(),
//                            user.username());
//
//        assertNotNull(changedUser);
//        assertTrue(changedUser.isEnabled());
//    }
//
//    // 测试是否是组成员
//    public void testIsGroupMember() throws Exception {
//        Group parentGroup = this.group1Aggregate();
//        DomainRegistry.groupRepository().add(parentGroup);
//
//        Group childGroup = this.group2Aggregate();
//        DomainRegistry.groupRepository().add(childGroup);
//
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        assertEquals(0, parentGroup.groupMembers().size());
//        assertEquals(0, childGroup.groupMembers().size());
//
//        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());
//        childGroup.addUser(user);
//
//        assertTrue(
//                ApplicationServiceRegistry
//                    .identityApplicationService()
//                    .isGroupMember(
//                            parentGroup.tenantId().id(),
//                            parentGroup.name(),
//                            user.username()));
//
//        assertTrue(
//                ApplicationServiceRegistry
//                    .identityApplicationService()
//                    .isGroupMember(
//                            childGroup.tenantId().id(),
//                            childGroup.name(),
//                            user.username()));
//    }
//
//    // 测试删除组组成员
//    public void testRemoveGroupFromGroup() throws Exception {
//        Group parentGroup = this.group1Aggregate();
//        DomainRegistry.groupRepository().add(parentGroup);
//
//        Group childGroup = this.group2Aggregate();
//        DomainRegistry.groupRepository().add(childGroup);
//
//        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());
//
//        assertEquals(1, parentGroup.groupMembers().size());
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .removeGroupFromGroup(new RemoveGroupFromGroupCommand(
//                    parentGroup.tenantId().id(),
//                    parentGroup.name(),
//                    childGroup.name()));
//
//        assertEquals(0, parentGroup.groupMembers().size());
//    }
//
//    // 测试删除组用户成员
//    public void testRemoveUserFromGroup() throws Exception {
//        Group parentGroup = this.group1Aggregate();
//        DomainRegistry.groupRepository().add(parentGroup);
//
//        Group childGroup = this.group2Aggregate();
//        DomainRegistry.groupRepository().add(childGroup);
//
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        parentGroup.addGroup(childGroup, DomainRegistry.groupMemberService());
//        childGroup.addUser(user);
//
//        assertEquals(1, parentGroup.groupMembers().size());
//        assertEquals(1, childGroup.groupMembers().size());
//        assertTrue(parentGroup.isMember(user, DomainRegistry.groupMemberService()));
//        assertTrue(childGroup.isMember(user, DomainRegistry.groupMemberService()));
//
//        ApplicationServiceRegistry
//            .identityApplicationService()
//            .removeUserFromGroup(new RemoveUserFromGroupCommand(
//                childGroup.tenantId().id(),
//                childGroup.name(),
//                user.username()));
//
//        assertEquals(1, parentGroup.groupMembers().size());
//        assertEquals(0, childGroup.groupMembers().size());
//        assertFalse(parentGroup.isMember(user, DomainRegistry.groupMemberService()));
//        assertFalse(childGroup.isMember(user, DomainRegistry.groupMemberService()));
//    }
//
//    // 测试查询租赁
//    public void testQueryTenant() throws Exception {
//        Tenant tenant = this.tenantAggregate();
//
//        Tenant queriedTenant =
//                ApplicationServiceRegistry
//                    .identityApplicationService()
//                    .tenant(tenant.tenantId().id());
//
//        assertNotNull(queriedTenant);
//        assertEquals(tenant, queriedTenant);
//    }
//
//    // 测试查询用户
//    public void testQueryUser() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        User queriedUser =
//                ApplicationServiceRegistry
//                    .identityApplicationService()
//                    .user(user.tenantId().id(), user.username());
//
//        assertNotNull(user);
//        assertEquals(user, queriedUser);
//    }
//
//    // 测试查询用户描述
//    public void testQueryUserDescriptor() throws Exception {
//        User user = this.userAggregate();
//        DomainRegistry.userRepository().add(user);
//
//        UserDescriptor queriedUserDescriptor =
//                ApplicationServiceRegistry
//                    .identityApplicationService()
//                    .userDescriptor(user.tenantId().id(), user.username());
//
//        assertNotNull(user);
//        assertEquals(user.userDescriptor(), queriedUserDescriptor);
//    }
}
