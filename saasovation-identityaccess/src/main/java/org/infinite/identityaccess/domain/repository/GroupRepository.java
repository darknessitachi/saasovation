package org.infinite.identityaccess.domain.repository;

import java.util.Collection;

import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 组仓储
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:44:39
 * @version V1.0
 */
public interface GroupRepository {

	void add(Group aGroup);

	void remove(Group aGroup);
	
	Collection<Group> allGroups(TenantId aTenantId);

	Group groupNamed(TenantId aTenantId, String aName);
}
