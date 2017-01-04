package org.infinite.identityaccess.domain.repository;

import java.util.Collection;

import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;


/**
 * 用户仓储
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:15:16
 * @version V1.0
 */
public interface UserRepository {

	void add(User aUser);

	void remove(User aUser);
	
	User userWithUsername(TenantId aTenantId, String aUsername);
	
	User userFromAuthenticCredentials(TenantId aTenantId, String aUsername,
			String anEncryptedPassword);

	Collection<User> allSimilarlyNamedUsers(TenantId aTenantId,
			String aFirstNamePrefix, String aLastNamePrefix);
}
