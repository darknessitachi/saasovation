package org.infinite.identityaccess.infrastructure.persistence;

import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.StringType;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.repository.TenantRepository;

import com.rapidark.framework.persistence.CleanableStore;
import com.rapidark.framework.persistence.hibernate.HibernateSessionSupport;


/**
 * 租赁仓储Hibernate实现
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:23:27
 * @version V1.0
 */
public class HibernateTenantRepository
        extends HibernateSessionSupport
        implements TenantRepository , CleanableStore{

    public HibernateTenantRepository() {
        super();
    }

    @Override
    public void add(Tenant aTenant) {
        try {
            this.session().saveOrUpdate(aTenant);
        } catch (ConstraintViolationException e) {
            throw new IllegalStateException("Tenant is not unique.", e);
        }
    }

    @Override
    public TenantId nextIdentity() {
        return new TenantId(UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void remove(Tenant aTenant) {
        this.session().delete(aTenant);
    }

    @Override
    public Tenant tenantNamed(String aName) {
        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.identity.Tenant as _obj_ "
                + "where _obj_.name = ?");

        query.setParameter(0, aName, StringType.INSTANCE);

        return (Tenant) query.uniqueResult();
    }

    @Override
    public Tenant tenantOfId(TenantId aTenantId) {
        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.identity.Tenant as _obj_ "
                + "where _obj_.tenantId = ?");

        query.setParameter(0, aTenantId);

        return (Tenant) query.uniqueResult();
    }

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}
}
