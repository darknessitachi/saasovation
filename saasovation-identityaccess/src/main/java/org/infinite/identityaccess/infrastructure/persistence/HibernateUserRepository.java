package org.infinite.identityaccess.infrastructure.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.StringType;

import com.abigdreamer.infinity.persistence.hibernate.HibernateSessionSupport;
import com.github.rapidark.framework.persistence.CleanableStore;

import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.repository.UserRepository;


/**
 * 用户仓储Hibernate实现
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:24:24
 * @version V1.0
 */
public class HibernateUserRepository
        extends HibernateSessionSupport
        implements UserRepository , CleanableStore{

    public HibernateUserRepository() {
        super();
    }

    @Override
    public void add(User aUser) {
        try {
            this.session().saveOrUpdate(aUser);
        } catch (ConstraintViolationException e) {
            throw new IllegalStateException("User is not unique.", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<User> allSimilarlyNamedUsers(
            TenantId aTenantId,
            String aFirstNamePrefix,
            String aLastNamePrefix) {

        if (aFirstNamePrefix.endsWith("%") || aLastNamePrefix.endsWith("%")) {
            throw new IllegalArgumentException("Name prefixes must not include %.");
        }

        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.identity.User as _obj_ "
                + "where _obj_.tenantId = ? "
                +   "and _obj_.person.name.firstName like ? "
                +   "and _obj_.person.name.lastName like ?");

        query.setParameter(0, aTenantId);
        query.setParameter(1, aFirstNamePrefix + "%", StringType.INSTANCE);
        query.setParameter(2, aLastNamePrefix + "%", StringType.INSTANCE);

        return query.list();
    }

    @Override
    public void remove(User aUser) {
        this.session().delete(aUser);
    }

    @Override
    public User userFromAuthenticCredentials(
            TenantId aTenantId,
            String aUsername,
            String anEncryptedPassword) {

        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.identity.User as _obj_ "
                + "where _obj_.tenantId = ? "
                  + "and _obj_.username = ? "
                  + "and _obj_.password = ?");

        query.setParameter(0, aTenantId);
        query.setParameter(1, aUsername, StringType.INSTANCE);
        query.setParameter(2, anEncryptedPassword, StringType.INSTANCE);

        return (User) query.uniqueResult();
    }

    @Override
    public User userWithUsername(
            TenantId aTenantId,
            String aUsername) {

        Query query = this.session().createQuery(
                "from com.saasovation.identityaccess.domain.model.identity.User as _obj_ "
                + "where _obj_.tenantId = ? "
                  + "and _obj_.username = ?");

        query.setParameter(0, aTenantId);
        query.setParameter(1, aUsername, StringType.INSTANCE);

        return (User) query.uniqueResult();
    }

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}
}
