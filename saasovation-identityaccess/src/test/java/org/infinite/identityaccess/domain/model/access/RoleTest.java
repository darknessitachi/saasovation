package org.infinite.identityaccess.domain.model.access;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.event.access.GroupAssignedToRole;
import org.infinite.identityaccess.domain.event.access.GroupUnassignedFromRole;
import org.infinite.identityaccess.domain.event.access.UserAssignedToRole;
import org.infinite.identityaccess.domain.event.access.UserUnassignedFromRole;
import org.infinite.identityaccess.domain.event.identity.GroupGroupAdded;
import org.infinite.identityaccess.domain.event.identity.GroupGroupRemoved;
import org.infinite.identityaccess.domain.event.identity.GroupUserAdded;
import org.infinite.identityaccess.domain.event.identity.GroupUserRemoved;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 角色测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午5:04:35
 * @version V1.0
 */
public class RoleTest extends IdentityAccessTest {

    private int groupSomethingAddedCount;
    private int groupSomethingRemovedCount;
    private int roleSomethingAssignedCount;
    private int roleSomethingUnassignedCount;

    public RoleTest() {
        super();
    }

    // 测试准备角色
    public void testProvisionRole() throws Exception {
        Tenant tenant = this.tenantAggregate();
        Role role = tenant.provisionRole("Manager", "A manager role.");
        DomainRegistry.roleRepository().add(role);
        assertEquals(1, DomainRegistry.roleRepository().allRoles(tenant.tenantId()).size());
    }

    // 测试角色唯一
    public void testRoleUniqueness() throws Exception {
        Tenant tenant = this.tenantAggregate();
        Role role1 = tenant.provisionRole("Manager", "A manager role.");
        DomainRegistry.roleRepository().add(role1);

        boolean nonUnique = false;

        try {
            Role role2 = tenant.provisionRole("Manager", "A manager role.");
            DomainRegistry.roleRepository().add(role2);

            fail("Should have thrown exception for nonuniqueness.");

        } catch (IllegalStateException e) {
            nonUnique = true;
        }

        assertTrue(nonUnique);
    }

    // 测试将角色授权给组，组中的用户拥有该角色
    public void testUserIsInRole() throws Exception {
        Tenant tenant = this.tenantAggregate();
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        Role managerRole = tenant.provisionRole("Manager", "A manager role.", true);
        Group group = new Group(user.tenantId(), "Managers", "A group of managers.");
        DomainRegistry.groupRepository().add(group);
        
        managerRole.assignGroup(group, DomainRegistry.groupMemberService());
        DomainRegistry.roleRepository().add(managerRole);
        
        group.addUser(user);

        assertTrue(group.isMember(user, DomainRegistry.groupMemberService()));
        assertTrue(managerRole.isInRole(user, DomainRegistry.groupMemberService()));
    }

	// 测试将角色授权给组，组中没有的用户不拥有该角色
    public void testUserIsNotInRole() throws Exception {
        Tenant tenant = this.tenantAggregate();
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        Role managerRole = tenant.provisionRole("Manager", "A manager role.", true);
        Group group = tenant.provisionGroup("Managers", "A group of managers.");
        DomainRegistry.groupRepository().add(group);
        
        // 将角色授权给组
        managerRole.assignGroup(group, DomainRegistry.groupMemberService());
        
        DomainRegistry.roleRepository().add(managerRole);
        Role accountantRole = new Role(user.tenantId(), "Accountant", "An accountant role.");
        DomainRegistry.roleRepository().add(accountantRole);

        assertFalse(managerRole.isInRole(user, DomainRegistry.groupMemberService()));
        assertFalse(accountantRole.isInRole(user, DomainRegistry.groupMemberService()));
    }

    // 测试角色未授权组，测试失败
    public void testNoRoleInternalGroupsInFindGroupByName() throws Exception {
        Tenant tenant = this.tenantAggregate();
        
        Role roleA = tenant.provisionRole("RoleA", "A role of A.");
        DomainRegistry.roleRepository().add(roleA);

        boolean error = false;

        try {

            System.out.println("GROUP REPOSITORY: " + DomainRegistry.groupRepository());

            DomainRegistry
                .groupRepository()
                .groupNamed(
                        tenant.tenantId(),
                        roleA.group().name());

            fail("Should have thrown exception for invalid group name.");

        } catch (Exception e) {
            error = true;
        }

        assertTrue(error);
    }

	// 测试组添加成员事件未派发
    public void testInternalGroupAddedEventsNotPublished() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupAssignedToRole>() {
            @Override
            public void handleEvent(GroupAssignedToRole aDomainEvent) {
                ++roleSomethingAssignedCount;
            }

            @Override
            public Class<GroupAssignedToRole> subscribedToEventType() {
                return GroupAssignedToRole.class;
            }
        });
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupAdded>() {
            @Override
            public void handleEvent(GroupGroupAdded aDomainEvent) {
                ++groupSomethingAddedCount;
            }

            @Override
            public Class<GroupGroupAdded> subscribedToEventType() {
                return GroupGroupAdded.class;
            }
        });
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<UserAssignedToRole>() {
            @Override
            public void handleEvent(UserAssignedToRole aDomainEvent) {
                ++roleSomethingAssignedCount;
            }

            @Override
            public Class<UserAssignedToRole> subscribedToEventType() {
                return UserAssignedToRole.class;
            }
        });
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupUserAdded>() {
            @Override
            public void handleEvent(GroupUserAdded aDomainEvent) {
                ++groupSomethingAddedCount;
            }

            @Override
            public Class<GroupUserAdded> subscribedToEventType() {
                return GroupUserAdded.class;
            }
        });

        Tenant tenant = this.tenantAggregate();
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        Role managerRole = tenant.provisionRole("Manager", "A manager role.", true);
        Group group = new Group(user.tenantId(), "Managers", "A group of managers.");
        DomainRegistry.groupRepository().add(group);
        
        // 将角色分配给组
        managerRole.assignGroup(group, DomainRegistry.groupMemberService());
        // 将角色分配给用户
        managerRole.assignUser(user);
        DomainRegistry.roleRepository().add(managerRole);
        group.addUser(user); // legal add

        assertEquals(2, roleSomethingAssignedCount);
        assertEquals(1, groupSomethingAddedCount);
    }

    // 测试组删除成员事件未派发
    public void testInternalGroupRemovedEventsNotPublished() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupUnassignedFromRole>() {
            @Override
            public void handleEvent(GroupUnassignedFromRole aDomainEvent) {
                ++roleSomethingUnassignedCount;
            }

            @Override
            public Class<GroupUnassignedFromRole> subscribedToEventType() {
                return GroupUnassignedFromRole.class;
            }
        });
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupRemoved>() {
            @Override
            public void handleEvent(GroupGroupRemoved aDomainEvent) {
                ++groupSomethingRemovedCount;
            }

            @Override
            public Class<GroupGroupRemoved> subscribedToEventType() {
                return GroupGroupRemoved.class;
            }
        });
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<UserUnassignedFromRole>() {
            @Override
            public void handleEvent(UserUnassignedFromRole aDomainEvent) {
                ++roleSomethingUnassignedCount;
            }

            @Override
            public Class<UserUnassignedFromRole> subscribedToEventType() {
                return UserUnassignedFromRole.class;
            }
        });
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupUserRemoved>() {
            @Override
            public void handleEvent(GroupUserRemoved aDomainEvent) {
                ++groupSomethingRemovedCount;
            }

            @Override
            public Class<GroupUserRemoved> subscribedToEventType() {
                return GroupUserRemoved.class;
            }
        });

        Tenant tenant = this.tenantAggregate();
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        Role managerRole = tenant.provisionRole("Manager", "A manager role.", true);
        Group group = new Group(user.tenantId(), "Managers", "A group of managers.");
        DomainRegistry.groupRepository().add(group);
        managerRole.assignUser(user);
        managerRole.assignGroup(group, DomainRegistry.groupMemberService());
        DomainRegistry.roleRepository().add(managerRole);

        managerRole.unassignUser(user);
        managerRole.unassignGroup(group);

        assertEquals(2, roleSomethingUnassignedCount);
        assertEquals(0, groupSomethingRemovedCount);
    }
}
