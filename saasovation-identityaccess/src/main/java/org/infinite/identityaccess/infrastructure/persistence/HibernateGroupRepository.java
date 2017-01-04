package org.infinite.identityaccess.infrastructure.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.StringType;

import com.abigdreamer.infinity.persistence.hibernate.HibernateSessionSupport;
import com.github.rapidark.framework.persistence.CleanableStore;

import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.repository.GroupRepository;


/**
 * 组仓储Hibernate实现
 * 
 * @author Darkness
 * @date 2014-5-28 下午3:15:46
 * @version V1.0
 */
public class HibernateGroupRepository
        extends HibernateSessionSupport
        implements GroupRepository , CleanableStore{

    public HibernateGroupRepository() {
        super();
    }

    @Override
    public void add(Group aGroup) {
        try {
            this.session().saveOrUpdate(aGroup);
        } catch (ConstraintViolationException e) {
            throw new IllegalStateException("Group is not unique.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Group> allGroups(TenantId aTenantId) {
        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.identity.Group as _obj_ "
                + "where _obj_.tenantId = ? "
                  + "and _obj_.name not like '" + Group.ROLE_GROUP_PREFIX + "%'");

        query.setParameter(0, aTenantId);

        return (Collection<Group>) query.list();
    }

    @Override
    public Group groupNamed(TenantId aTenantId, String aName) {
        if (aName.startsWith(Group.ROLE_GROUP_PREFIX)) {
            throw new IllegalArgumentException("May not find internal groups.");
        }

        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.identity.Group as _obj_ "
                + "where _obj_.tenantId = ? "
                  + "and _obj_.name = ?");

        query.setParameter(0, aTenantId);
        query.setParameter(1, aName, StringType.INSTANCE);

        return (Group) query.uniqueResult();
    }

    @Override
    public void remove(Group aGroup) {
        this.session().delete(aGroup);
    }

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}
}
