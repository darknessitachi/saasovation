package org.infinite.identityaccess.domain.model.identity;

import java.util.Collection;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventSubscriber;
import org.infinite.identityaccess.domain.DomainRegistry;
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
 * 组测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午3:13:30
 * @version V1.0
 */
public class GroupTest extends IdentityAccessTest {

    private int groupGroupAddedCount;
    private int groupGroupRemovedCount;
    private int groupUserAddedCount;
    private int groupUserRemovedCount;

    public GroupTest() {
        super();
    }

    // 测试准备组
    public void testProvisionGroup() throws Exception {
        Tenant tenant = this.tenantAggregate();
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);
        assertEquals(1, DomainRegistry.groupRepository().allGroups(tenant.tenantId()).size());
    }

    // 测试组添加组成员
    public void testAddGroup() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupAdded>() {
            @Override
            public void handleEvent(GroupGroupAdded aDomainEvent) {
                ++groupGroupAddedCount;
            }

            @Override
            public Class<GroupGroupAdded> subscribedToEventType() {
                return GroupGroupAdded.class;
            }
        });

        Tenant tenant = this.tenantAggregate();
        
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);
        
        Group groupB = tenant.provisionGroup("GroupB", "A group named GroupB");
        DomainRegistry.groupRepository().add(groupB);
        
        groupA.addGroup(groupB, DomainRegistry.groupMemberService());
        
        assertEquals(1, groupA.groupMembers().size());
        assertEquals(0, groupB.groupMembers().size());
        assertEquals(1, groupGroupAddedCount);
    }

    // 测试添加人员
    public void testAddUser() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupUserAdded>() {
            @Override
            public void handleEvent(GroupUserAdded aDomainEvent) {
                ++groupUserAddedCount;
            }

            @Override
            public Class<GroupUserAdded> subscribedToEventType() {
                return GroupUserAdded.class;
            }
        });

        Tenant tenant = this.tenantAggregate();
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        groupA.addUser(user);
        DomainRegistry.groupRepository().add(groupA);
        
        assertEquals(1, groupA.groupMembers().size());
        assertTrue(groupA.isMember(user, DomainRegistry.groupMemberService()));
        assertEquals(1, groupUserAddedCount);
    }

    // 测试删除组
    public void testRemoveGroup() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupRemoved>() {
            @Override
            public void handleEvent(GroupGroupRemoved aDomainEvent) {
                ++groupGroupRemovedCount;
            }

            @Override
            public Class<GroupGroupRemoved> subscribedToEventType() {
                return GroupGroupRemoved.class;
            }
        });

        Tenant tenant = this.tenantAggregate();
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);

        Group groupB = tenant.provisionGroup("GroupB", "A group named GroupB");
        DomainRegistry.groupRepository().add(groupB);
        
        groupA.addGroup(groupB, DomainRegistry.groupMemberService());

        assertEquals(1, groupA.groupMembers().size());
        
        groupA.removeGroup(groupB);
        assertEquals(0, groupA.groupMembers().size());
        assertEquals(1, groupGroupRemovedCount);
    }

    // 测试删除用户
    public void testRemoveUser() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupUserRemoved>() {
            @Override
            public void handleEvent(GroupUserRemoved aDomainEvent) {
                ++groupUserRemovedCount;
            }

            @Override
            public Class<GroupUserRemoved> subscribedToEventType() {
                return GroupUserRemoved.class;
            }
        });

        Tenant tenant = this.tenantAggregate();
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        groupA.addUser(user);
        DomainRegistry.groupRepository().add(groupA);

        assertEquals(1, groupA.groupMembers().size());
        
        groupA.removeUser(user);
        assertEquals(0, groupA.groupMembers().size());
        assertEquals(1, groupUserRemovedCount);
    }

    // 测试删除组用户
    public void testRemoveGroupReferencedUser() throws Exception {
        Tenant tenant = this.tenantAggregate();
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        groupA.addUser(user);
        DomainRegistry.groupRepository().add(groupA);// 添加组

        assertEquals(groupA.groupMembers().size(), 1);
        assertTrue(groupA.isMember(user, DomainRegistry.groupMemberService()));
        DomainRegistry.userRepository().remove(user);// 删除用户
        this.session().flush();
        this.session().evict(groupA);// hibernate回收组
        
        Group reGrouped =
            DomainRegistry
                .groupRepository()
                .groupNamed(tenant.tenantId(), "GroupA");
        assertEquals("GroupA", reGrouped.name());
        assertEquals(1, reGrouped.groupMembers().size());
        assertFalse(reGrouped.isMember(user, DomainRegistry.groupMemberService()));
    }

    // 测试通过仓储删除组
    public void testRepositoryRemoveGroup() throws Exception {
        Tenant tenant = this.tenantAggregate();
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);
        Group notNullGroup =
            DomainRegistry
                .groupRepository()
                .groupNamed(tenant.tenantId(), "GroupA");
        assertNotNull(notNullGroup);
        DomainRegistry.groupRepository().remove(groupA);
        Group nullGroup =
            DomainRegistry
                .groupRepository()
                .groupNamed(tenant.tenantId(), "GroupA");
        assertNull(nullGroup);
    }

    // 测试用户是否是嵌套组的成员
    public void testUserIsMemberOfNestedGroup() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupAdded>() {
            @Override
            public void handleEvent(GroupGroupAdded aDomainEvent) {
                ++groupGroupAddedCount;
            }

            @Override
            public Class<GroupGroupAdded> subscribedToEventType() {
                return GroupGroupAdded.class;
            }
        });

        Tenant tenant = this.tenantAggregate();
        
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);
        
        Group groupB = tenant.provisionGroup("GroupB", "A group named GroupB");
        DomainRegistry.groupRepository().add(groupB);
        
        groupA.addGroup(groupB, DomainRegistry.groupMemberService());
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        groupB.addUser(user);

        assertTrue(groupB.isMember(user, DomainRegistry.groupMemberService()));
        assertTrue(groupA.isMember(user, DomainRegistry.groupMemberService()));

        assertEquals(1, groupGroupAddedCount);
    }

    // 测试用户非组成员
    public void testUserIsNotMember() throws Exception {
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        // tests alternate creation via constructor
        Group groupA = new Group(user.tenantId(), "GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);
        
        Group groupB = new Group(user.tenantId(), "GroupB", "A group named GroupB");
        DomainRegistry.groupRepository().add(groupB);
        
        groupA.addGroup(groupB, DomainRegistry.groupMemberService());

        assertFalse(groupB.isMember(user, DomainRegistry.groupMemberService()));
        assertFalse(groupA.isMember(user, DomainRegistry.groupMemberService()));
    }

    // 测试非环形依赖分组
    public void testNoRecursiveGroupings() throws Exception {
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<GroupGroupAdded>() {
            @Override
            public void handleEvent(GroupGroupAdded aDomainEvent) {
                ++groupGroupAddedCount;
            }

            @Override
            public Class<GroupGroupAdded> subscribedToEventType() {
                return GroupGroupAdded.class;
            }
        });

        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        // tests alternate creation via constructor
        Group groupA = new Group(user.tenantId(), "GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);
        
        Group groupB = new Group(user.tenantId(), "GroupB", "A group named GroupB");
        DomainRegistry.groupRepository().add(groupB);
        
        Group groupC = new Group(user.tenantId(), "GroupC", "A group named GroupC");
        DomainRegistry.groupRepository().add(groupC);
        
        groupA.addGroup(groupB, DomainRegistry.groupMemberService());
        groupB.addGroup(groupC, DomainRegistry.groupMemberService());

        boolean failed = false;

        try {
            groupC.addGroup(groupA, DomainRegistry.groupMemberService());
        } catch (Throwable t) {
            failed = true;
        }

        assertTrue(failed);

        assertEquals(2, groupGroupAddedCount);
    }

    // 测试没有角色内部组
    public void testNoRoleInternalGroupsInFindAllGroups() throws Exception {
        Tenant tenant = this.tenantAggregate();
        
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        DomainRegistry.groupRepository().add(groupA);

        Role roleA = tenant.provisionRole("RoleA", "A role of A.");
        DomainRegistry.roleRepository().add(roleA);
        
        Role roleB = tenant.provisionRole("RoleB", "A role of B.");
        DomainRegistry.roleRepository().add(roleB);
        
        Role roleC = tenant.provisionRole("RoleC", "A role of C.");
        DomainRegistry.roleRepository().add(roleC);

        Collection<Group> groups =
            DomainRegistry
                .groupRepository()
                .allGroups(tenant.tenantId());

        assertEquals(1, groups.size());
    }
}
