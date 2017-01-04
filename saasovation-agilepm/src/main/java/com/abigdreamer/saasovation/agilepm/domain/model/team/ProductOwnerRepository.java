package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Collection;

import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  产品负责人仓储
 * 
 * @author Darkness
 * @date 2014-5-8 下午9:06:32 
 * @version V1.0
 */
public interface ProductOwnerRepository {

	Collection<ProductOwner> allProductOwnersOfTenant(TenantId aTenantId);

	ProductOwner productOwnerOfIdentity(TenantId aTenantId, String aUsername);

	void remove(ProductOwner aProductOwner);

	void removeAll(Collection<ProductOwner> aProductOwnerCollection);

	void save(ProductOwner aProductOwner);

	void saveAll(Collection<ProductOwner> aProductOwnerCollection);
}
