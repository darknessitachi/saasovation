package org.infinite.identityaccess.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.repository.TenantRepository;

import com.rapidark.framework.persistence.CleanableStore;


/**
 * 租赁内存仓储
 * 
 * @author Darkness
 * @date 2014-11-28 上午10:04:12 
 * @version V1.0
 */
public class InMemoryTenantRepository implements TenantRepository, CleanableStore {

    private Map<String,Tenant> repository;

    public InMemoryTenantRepository() {
        super();

        this.repository = new HashMap<String,Tenant>();
    }

    @Override
    public void add(Tenant aTenant) {
        String key = this.keyOf(aTenant);

        if (this.repository().containsKey(key)) {
            throw new IllegalStateException("Duplicate key.");
        }

        this.repository().put(key, aTenant);
    }

    @Override
    public TenantId nextIdentity() {
        return new TenantId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public Tenant tenantNamed(String aName) {
        for (Tenant tenant : this.repository().values()) {
            if (tenant.name().equals(aName)) {
                return tenant;
            }
        }

        return null;
    }

    @Override
    public Tenant tenantOfId(TenantId aTenantId) {
        return this.repository().get(this.keyOf(aTenantId));
    }

    @Override
    public void remove(Tenant aTenant) {
        String key = this.keyOf(aTenant);

        this.repository().remove(key);
    }

    @Override
    public void clean() {
        this.repository().clear();
    }

    private String keyOf(TenantId aTenantId) {
        String key = aTenantId.id();

        return key;
    }

    private String keyOf(Tenant aTenant) {
        return this.keyOf(aTenant.tenantId());
    }

    private Map<String,Tenant> repository() {
        return this.repository;
    }
}
