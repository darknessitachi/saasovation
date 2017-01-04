package org.infinite.identityaccess.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.repository.RoleRepository;

import com.github.rapidark.framework.persistence.CleanableStore;


/**
 * 角色内存仓储
 * 
 * @author Darkness
 * @date 2014-11-28 上午10:04:55 
 * @version V1.0
 */
public class InMemoryRoleRepository implements RoleRepository, CleanableStore {

    private Map<String,Role> repository;

    public InMemoryRoleRepository() {
        super();

        this.repository = new HashMap<String,Role>();
    }

    @Override
    public void add(Role aRole) {
        String key = this.keyOf(aRole);

        if (this.repository().containsKey(key)) {
            throw new IllegalStateException("Duplicate key.");
        }

        this.repository().put(key, aRole);
    }

    @Override
    public Collection<Role> allRoles(TenantId aTenantId) {
        Collection<Role> roles = new ArrayList<Role>();

        for (Role role : this.repository().values()) {
            if (role.tenantId().equals(aTenantId)) {
                roles.add(role);
            }
        }

        return roles;
    }

    @Override
    public void remove(Role aRole) {
        String key = this.keyOf(aRole);

        this.repository().remove(key);
    }

    @Override
    public Role roleNamed(TenantId aTenantId, String aRoleName) {
        return this.repository().get(this.keyOf(aTenantId, aRoleName));
    }

    @Override
    public void clean() {
        this.repository().clear();
    }

    private String keyOf(TenantId aTenantId, String aRoleName) {
        String key = aTenantId.id() + "#" + aRoleName;

        return key;
    }

    private String keyOf(Role aRole) {
        return this.keyOf(aRole.tenantId(), aRole.name());
    }

    private Map<String,Role> repository() {
        return this.repository;
    }
}
