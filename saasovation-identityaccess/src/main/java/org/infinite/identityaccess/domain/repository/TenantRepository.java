package org.infinite.identityaccess.domain.repository;

import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.TenantId;


/**
 * 租赁仓储
 * 
 * @author Darkness
 * @date 2014-5-27 下午7:55:46
 * @version V1.0
 */
public interface TenantRepository {

	void add(Tenant aTenant);

	void remove(Tenant aTenant);
	
	TenantId nextIdentity();

	Tenant tenantOfId(TenantId aTenantId);
	
	Tenant tenantNamed(String aName);
}
