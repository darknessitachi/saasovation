package org.infinite.identityaccess.infrastructure.persistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.repository.GroupRepository;

import com.rapidark.framework.persistence.CleanableStore;


/**
 * 组内存仓储
 * 
 * @author Darkness
 * @date 2014-11-28 上午10:07:19 
 * @version V1.0
 */
public class InMemoryGroupRepository implements GroupRepository, CleanableStore {

    private Map<String,Group> repository;

    public InMemoryGroupRepository() {
        super();

        this.repository = new HashMap<String,Group>();
    }

    @Override
    public void add(Group aGroup) {
        String key = this.keyOf(aGroup);

        if (this.repository().containsKey(key)) {
            throw new IllegalStateException("Duplicate key.");
        }

        this.repository().put(key, aGroup);
    }

    @Override
    public Collection<Group> allGroups(TenantId aTenantId) {
        List<Group> groups = new ArrayList<Group>();

        for (Group group : this.repository().values()) {
            if (group.tenantId().equals(aTenantId)) {
                groups.add(group);
            }
        }

        return groups;
    }

    @Override
    public Group groupNamed(TenantId aTenantId, String aName) {
        if (aName.startsWith(Group.ROLE_GROUP_PREFIX)) {
            throw new IllegalArgumentException("May not find internal groups.");
        }

        String key = this.keyOf(aTenantId, aName);

        return this.repository().get(key);
    }

    @Override
    public void remove(Group aGroup) {
        String key = this.keyOf(aGroup);

        this.repository().remove(key);
    }

    @Override
    public void clean() {
        this.repository().clear();
    }

    private String keyOf(TenantId aTenantId, String aName) {
        String key = aTenantId.id() + "#" + aName;

        return key;
    }

    private String keyOf(Group aGroup) {
        return this.keyOf(aGroup.tenantId(), aGroup.name());
    }

    private Map<String,Group> repository() {
        return this.repository;
    }
}
