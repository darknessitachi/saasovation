package com.abigdreamer.saasovation.agilepm.port.adapter.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.iq80.leveldb.DB;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwnerId;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;
import com.rapidark.framework.persistence.leveldb.LevelDBProvider;
import com.rapidark.framework.persistence.leveldb.LevelDBUnitOfWork;

import junit.framework.TestCase;


public class LevelDBProductRepositoryTest extends TestCase {

    private DB database;
    private ProductRepository productRepository = new LevelDBProductRepository();

    public LevelDBProductRepositoryTest() {
        super();
    }

    public void testSave() throws Exception {
        TenantId tenantId = new TenantId("T12345");

        Product product =
                new Product(
                        tenantId,
                        new ProductId("679890"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product",
                        "My product, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        LevelDBUnitOfWork.start(this.database);
        productRepository.save(product);
        LevelDBUnitOfWork.current().commit();

        Product savedProduct =
                productRepository
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertNotNull(savedProduct);
        assertEquals(product.tenantId(), savedProduct.tenantId());
        assertEquals(product.productId(), savedProduct.productId());
        assertEquals(product.productOwnerId(), savedProduct.productOwnerId());
        assertEquals("My Product", savedProduct.name());
        assertEquals("My product, which is my product.", savedProduct.description());
        assertEquals(DiscussionAvailability.NOT_REQUESTED, savedProduct.discussion().availability());

        Collection<Product> savedProducts =
                productRepository
                    .allProductsOfTenant(product.tenantId());

        assertFalse(savedProducts.isEmpty());
        assertEquals(1, savedProducts.size());
    }

    public void testStartDiscussionInitiationSave() throws Exception {
        TenantId tenantId = new TenantId("T12345");

        Product product =
                new Product(
                        tenantId,
                        new ProductId("679890"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product",
                        "My product, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        product.startDiscussionInitiation("ABCDEFGHIJ");

        LevelDBUnitOfWork.start(this.database);

        productRepository.save(product);

        LevelDBUnitOfWork.current().commit();

        Product savedProduct =
                productRepository
                    .productOfDiscussionInitiationId(
                            product.tenantId(),
                            "ABCDEFGHIJ");

        assertNotNull(savedProduct);
        assertEquals(product.tenantId(), savedProduct.tenantId());
        assertEquals(product.productId(), savedProduct.productId());
        assertEquals(product.productOwnerId(), savedProduct.productOwnerId());
        assertEquals("My Product", savedProduct.name());
        assertEquals("My product, which is my product.", savedProduct.description());
        assertEquals(DiscussionAvailability.NOT_REQUESTED, savedProduct.discussion().availability());
    }

    public void testRemove() throws Exception {
        TenantId tenantId = new TenantId("T12345");

        Product product1 =
                new Product(
                        tenantId,
                        new ProductId("679890"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product 1",
                        "My product 1, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        Product product2 =
                new Product(
                        tenantId,
                        new ProductId("09876"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product 2",
                        "My product 2, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        LevelDBUnitOfWork.start(this.database);
        productRepository.save(product1);
        productRepository.save(product2);
        LevelDBUnitOfWork.current().commit();

        LevelDBUnitOfWork.start(this.database);
        productRepository.remove(product1);
        LevelDBUnitOfWork.current().commit();

        Collection<Product> savedProducts = productRepository.allProductsOfTenant(tenantId);
        assertFalse(savedProducts.isEmpty());
        assertEquals(1, savedProducts.size());
        assertEquals(product2.productId(), savedProducts.iterator().next().productId());

        LevelDBUnitOfWork.start(this.database);
        productRepository.remove(product2);
        LevelDBUnitOfWork.current().commit();

        savedProducts = productRepository.allProductsOfTenant(tenantId);
        assertTrue(savedProducts.isEmpty());
    }

    public void testSaveAllRemoveAll() throws Exception {
        TenantId tenantId = new TenantId("T12345");

        Product product1 =
                new Product(
                        tenantId,
                        new ProductId("679890"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product 1",
                        "My product 1, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        Product product2 =
                new Product(
                        tenantId,
                        new ProductId("09876"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product 2",
                        "My product 2, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        Product product3 =
                new Product(
                        tenantId,
                        new ProductId("100200300"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product 3",
                        "My product 3, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        LevelDBUnitOfWork.start(this.database);
        productRepository.saveAll(Arrays.asList(new Product[] { product1, product2, product3 }));
        LevelDBUnitOfWork.current().commit();

        Collection<Product> savedProducts = productRepository.allProductsOfTenant(tenantId);
        assertFalse(savedProducts.isEmpty());
        assertEquals(3, savedProducts.size());

        LevelDBUnitOfWork.start(this.database);
        productRepository.removeAll(Arrays.asList(new Product[] { product1, product3 }));
        LevelDBUnitOfWork.current().commit();

        savedProducts = productRepository.allProductsOfTenant(tenantId);
        assertFalse(savedProducts.isEmpty());
        assertEquals(1, savedProducts.size());
        assertEquals(product2.productId(), savedProducts.iterator().next().productId());

        LevelDBUnitOfWork.start(this.database);
        productRepository.removeAll(Arrays.asList(new Product[] { product2 }));
        LevelDBUnitOfWork.current().commit();

        savedProducts = productRepository.allProductsOfTenant(tenantId);
        assertTrue(savedProducts.isEmpty());
    }

    public void testConcurrentTransactions() throws Exception {
        final List<Integer> orderOfCommits = new ArrayList<Integer>();

        final TenantId tenantId = new TenantId("T12345");

        Product product1 =
                new Product(
                        tenantId,
                        new ProductId("679890"),
                        new ProductOwnerId(tenantId, "thepm"),
                        "My Product 1",
                        "My product 1, which is my product.",
                        DiscussionAvailability.NOT_REQUESTED);

        LevelDBUnitOfWork.start(database);
        productRepository.save(product1);

        new Thread() {
           @Override
           public void run() {
               Product product2 =
                       new Product(
                               tenantId,
                               new ProductId("09876"),
                               new ProductOwnerId(tenantId, "thepm"),
                               "My Product 2",
                               "My product 2, which is my product.",
                               DiscussionAvailability.NOT_REQUESTED);

               LevelDBUnitOfWork.start(database);
               productRepository.save(product2);
               LevelDBUnitOfWork.current().commit();
               orderOfCommits.add(2);
           }
        }.start();

        Thread.sleep(250L);

        LevelDBUnitOfWork.current().commit();
        orderOfCommits.add(1);

        for (int idx = 0; idx < orderOfCommits.size(); ++idx) {
            assertEquals(idx + 1, orderOfCommits.get(idx).intValue());
        }

        Thread.sleep(250L);

        Collection<Product> savedProducts =
                productRepository.allProductsOfTenant(product1.tenantId());

        assertFalse(savedProducts.isEmpty());
        assertEquals(2, savedProducts.size());
    }

    @Override
    protected void setUp() throws Exception {
        DomainEventPublisher.instance().reset();

        this.database = LevelDBProvider.instance().databaseFrom(LevelDBDatabasePath.agilePMPath());

        LevelDBProvider.instance().purge(this.database);

        super.setUp();
    }
}
