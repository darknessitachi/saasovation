package com.abigdreamer.saasovation.agilepm.application;

import java.util.Date;

import com.abigdreamer.saasovation.agilepm.domain.model.DomainRegistry;
import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductCommonTest;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwner;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;
import com.rapidark.framework.persistence.leveldb.LevelDBUnitOfWork;


public abstract class ProductApplicationCommonTest extends ProductCommonTest {

    protected Product persistedProductForTest() {
        Product product = this.productForTest();

        LevelDBUnitOfWork.start(this.database);

        DomainRegistry.productRepository().save(product);

        LevelDBUnitOfWork.current().commit();

        return product;
    }

    protected ProductOwner persistedProductOwnerForTest() {
        ProductOwner productOwner =
                new ProductOwner(
                        new TenantId("T-12345"),
                        "zoe",
                        "Zoe",
                        "Doe",
                        "zoe@saasovation.com",
                        new Date(new Date().getTime() - (86400000L * 30)));

        LevelDBUnitOfWork.start(this.database);

        DomainRegistry.productOwnerRepository().save(productOwner);

        LevelDBUnitOfWork.current().commit();

        return productOwner;
    }

}
