package com.abigdreamer.saasovation.agilepm.domain.model.product;

import java.util.Collection;

import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  产品仓储
 * 
 * @author Darkness
 * @date 2014-5-8 下午7:21:07 
 * @version V1.0
 */
public interface ProductRepository {

	Collection<Product> allProductsOfTenant(TenantId aTenantId);

	ProductId nextIdentity();

	Product productOfDiscussionInitiationId(TenantId aTenantId, String aDiscussionInitiationId);

	Product productOfId(TenantId aTenantId, ProductId aProductId);

	void remove(Product aProduct);

	void removeAll(Collection<Product> aProductCollection);

	void save(Product aProduct);

	void saveAll(Collection<Product> aProductCollection);
}
