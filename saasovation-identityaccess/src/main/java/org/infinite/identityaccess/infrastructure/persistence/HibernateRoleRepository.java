package org.infinite.identityaccess.infrastructure.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.StringType;
import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.repository.RoleRepository;

import com.rapidark.framework.persistence.CleanableStore;
import com.rapidark.framework.persistence.hibernate.HibernateSessionSupport;


/**
 * 角色仓储Hibernate实现
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:23:07
 * @version V1.0
 */
public class HibernateRoleRepository
        extends HibernateSessionSupport
        implements RoleRepository , CleanableStore{

    public HibernateRoleRepository() {
        super();
    }

    @Override
    public void add(Role aRole) {
        try {
            this.session().saveOrUpdate(aRole);
        } catch (ConstraintViolationException e) {
            throw new IllegalStateException("Role is not unique.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Role> allRoles(TenantId aTenantId) {
        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.access.Role as _obj_ "
                + "where _obj_.tenantId = ?");

        query.setParameter(0, aTenantId);

        return (Collection<Role>) query.list();
    }

    @Override
    public void remove(Role aRole) {
        this.session().delete(aRole);
    }

    @Override
    public Role roleNamed(TenantId aTenantId, String aRoleName) {
        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.access.Role as _obj_ "
                + "where _obj_.tenantId = ? "
                  + "and _obj_.name = ?");

        query.setParameter(0, aTenantId);
        query.setParameter(1, aRoleName, StringType.INSTANCE);

        return (Role) query.uniqueResult();
    }

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}
}
