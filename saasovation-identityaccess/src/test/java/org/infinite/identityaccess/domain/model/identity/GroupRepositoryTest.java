package org.infinite.identityaccess.domain.model.identity;

import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 组仓储测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午4:48:19
 * @version V1.0
 */
public class GroupRepositoryTest extends IdentityAccessTest {

    public GroupRepositoryTest() {
        super();
    }

    // 测试删除组用户
    public void testRemoveGroupReferencedUser() throws Exception {
        Tenant tenant = this.tenantAggregate();
        Group groupA = tenant.provisionGroup("GroupA", "A group named GroupA");
        
        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);
        
        groupA.addUser(user);
        DomainRegistry.groupRepository().add(groupA);

        assertEquals(groupA.groupMembers().size(), 1);
        assertTrue(groupA.isMember(user, DomainRegistry.groupMemberService()));
        
        DomainRegistry.userRepository().remove(user);
        this.session().flush();
        this.session().evict(groupA);
        
        Group reGrouped =
            DomainRegistry
                .groupRepository()
                .groupNamed(tenant.tenantId(), "GroupA");
        assertEquals("GroupA", reGrouped.name());
        assertEquals(1, reGrouped.groupMembers().size());
        assertFalse(reGrouped.isMember(user, DomainRegistry.groupMemberService()));
    }

    // 测试删除组
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
}
