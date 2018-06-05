package org.infinite.identityaccess.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.infinite.identityaccess.domain.model.identity.FullName;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.repository.UserRepository;

import com.rapidark.framework.persistence.CleanableStore;


/**
 * 用户内存仓储
 * 
 * @author Darkness
 * @date 2014-11-28 上午10:05:51 
 * @version V1.0
 */
public class InMemoryUserRepository implements UserRepository, CleanableStore {

    private Map<String,User> repository;

    public InMemoryUserRepository() {
        super();

        this.repository = new HashMap<String,User>();
    }

    @Override
    public void add(User aUser) {
        String key = this.keyOf(aUser);

        if (this.repository().containsKey(key)) {
            throw new IllegalStateException("Duplicate key.");
        }

        this.repository().put(key, aUser);
    }

    @Override
    public Collection<User> allSimilarlyNamedUsers(
            TenantId aTenantId,
            String aFirstNamePrefix,
            String aLastNamePrefix) {

        Collection<User> users = new ArrayList<User>();

        aFirstNamePrefix = aFirstNamePrefix.toLowerCase();
        aLastNamePrefix = aLastNamePrefix.toLowerCase();

        for (User user : this.repository().values()) {
            if (user.tenantId().equals(aTenantId)) {
                FullName name = user.person().name();
                if (name.firstName().toLowerCase().startsWith(aFirstNamePrefix)) {
                    if (name.lastName().toLowerCase().startsWith(aLastNamePrefix)) {
                        users.add(user);
                    }
                }
            }
        }

        return users;
    }

    @Override
    public void remove(User aUser) {
        String key = this.keyOf(aUser);

        this.repository().remove(key);
    }

    @Override
    public User userFromAuthenticCredentials(
            TenantId aTenantId,
            String aUsername,
            String anEncryptedPassword) {

        for (User user : this.repository().values()) {
            if (user.tenantId().equals(aTenantId)) {
                if (user.username().equals(aUsername)) {
                    if (user.internalAccessOnlyEncryptedPassword().equals(anEncryptedPassword)) {
                        return user;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public User userWithUsername(TenantId aTenantId, String aUsername) {
        for (User user : this.repository().values()) {
            if (user.tenantId().equals(aTenantId)) {
                if (user.username().equals(aUsername)) {
                    return user;
                }
            }
        }

        return null;
    }

    @Override
    public void clean() {
        this.repository().clear();
    }

    private String keyOf(TenantId aTenantId, String aUsername) {
        String key = aTenantId.id() + "#" + aUsername;

        return key;
    }

    private String keyOf(User aUser) {
        return this.keyOf(aUser.tenantId(), aUser.username());
    }

    private Map<String,User> repository() {
        return this.repository;
    }
}
