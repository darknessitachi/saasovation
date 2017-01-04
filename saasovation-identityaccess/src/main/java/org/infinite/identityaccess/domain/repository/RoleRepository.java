package org.infinite.identityaccess.domain.repository;

import java.util.Collection;

import org.infinite.identityaccess.domain.model.access.Role;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 角色仓储
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:24:09
 * @version V1.0
 */
public interface RoleRepository {

	void add(Role aRole);
	
	void remove(Role aRole);

	Collection<Role> allRoles(TenantId aTenantId);

	Role roleNamed(TenantId aTenantId, String aRoleName);
}
