package org.infinite.identityaccess.domain.model.identity;

import java.util.Collection;

import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 用户仓储测试
 * 
 * @author Darkness
 * @date 2014-5-27 下午9:18:14
 * @version V1.0
 */
public class UserRepositoryTest extends IdentityAccessTest {

	// 测试添加用户
    public void testAddUser() throws Exception {

        User user = this.userAggregate();

        DomainRegistry.userRepository().add(user);

        assertNotNull(DomainRegistry
                    .userRepository()
                    .userWithUsername(user.tenantId(), user.username()));
    }

	// 测试根据用户名查找用户
    public void testFindUserByUsername() throws Exception {

        User user = this.userAggregate();

        DomainRegistry.userRepository().add(user);

        assertNotNull(DomainRegistry
                .userRepository()
                .userWithUsername(user.tenantId(), user.username()));
    }

    // 测试删除用户
    public void testRemoveUser() throws Exception {

        User user = this.userAggregate();

        DomainRegistry.userRepository().add(user);

        assertNotNull(DomainRegistry
                    .userRepository()
                    .userWithUsername(user.tenantId(), user.username()));

        DomainRegistry.userRepository().remove(user);

        assertNull(DomainRegistry
                    .userRepository()
                    .userWithUsername(user.tenantId(), user.username()));
    }

    // 测试查找相似姓名的用户
    public void testFindSimilarlyNamedUsers() throws Exception {

        User user = this.userAggregate();
        DomainRegistry.userRepository().add(user);

        User user2 = this.userAggregate2();
        DomainRegistry.userRepository().add(user2);

        FullName name = user.person().name();

        Collection<User> users =
            DomainRegistry
                .userRepository()
                .allSimilarlyNamedUsers(
                        user.tenantId(),
                        "",
                        name.lastName().substring(0, 2));

        assertEquals(users.size(), 2);
    }

}
