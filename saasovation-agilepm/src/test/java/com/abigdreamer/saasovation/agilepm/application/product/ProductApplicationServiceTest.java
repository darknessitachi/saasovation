package com.abigdreamer.saasovation.agilepm.application.product;

import java.util.UUID;


import com.abigdreamer.saasovation.agilepm.application.ApplicationServiceRegistry;
import com.abigdreamer.saasovation.agilepm.application.ProductApplicationCommonTest;
import com.abigdreamer.saasovation.agilepm.application.product.InitiateDiscussionCommand;
import com.abigdreamer.saasovation.agilepm.application.product.NewProductCommand;
import com.abigdreamer.saasovation.agilepm.application.product.RequestProductDiscussionCommand;
import com.abigdreamer.saasovation.agilepm.application.product.RetryProductDiscussionRequestCommand;
import com.abigdreamer.saasovation.agilepm.application.product.StartDiscussionInitiationCommand;
import com.abigdreamer.saasovation.agilepm.domain.model.DomainRegistry;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwner;


/**
 *  产品应用服务测试
 * 
 * @author Darkness
 * @date 2014-5-8 下午9:05:05 
 * @version V1.0
 */
public class ProductApplicationServiceTest extends ProductApplicationCommonTest {

    public ProductApplicationServiceTest() {
        super();
    }

    public void testDiscussionProcess() throws Exception {
        Product product = this.persistedProductForTest();

        ApplicationServiceRegistry.productApplicationService().requestProductDiscussion(
                new RequestProductDiscussionCommand(
                        product.tenantId().id(),
                        product.productId().id()));

        ApplicationServiceRegistry.productApplicationService().startDiscussionInitiation(
                new StartDiscussionInitiationCommand(
                        product.tenantId().id(),
                        product.productId().id()));

        Product productWithStartedDiscussionInitiation =
                DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertNotNull(productWithStartedDiscussionInitiation.discussionInitiationId());

        String discussionId = UUID.randomUUID().toString().toUpperCase();

        InitiateDiscussionCommand command =
                new InitiateDiscussionCommand(
                        product.tenantId().id(),
                        product.productId().id(),
                        discussionId);

        ApplicationServiceRegistry.productApplicationService().initiateDiscussion(command);

        Product productWithInitiatedDiscussion =
                DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertEquals(discussionId, productWithInitiatedDiscussion.discussion().descriptor().id());
    }

    /**
     *  创建产品
     * 
     * @author Darkness
     * @date 2014-5-8 下午9:05:28 
     * @version V1.0
     */
    public void testNewProduct() throws Exception {
        ProductOwner productOwner = this.persistedProductOwnerForTest();

        String newProductId =
        		ApplicationServiceRegistry.productApplicationService().newProduct(
                    new NewProductCommand(
                            "T-12345",
                            productOwner.productOwnerId().id(),
                            "My Product",
                            "The description of My Product."));

        Product newProduct =
                DomainRegistry.productRepository()
                    .productOfId(
                            productOwner.tenantId(),
                            new ProductId(newProductId));

        assertNotNull(newProduct);
        assertEquals("My Product", newProduct.name());
        assertEquals("The description of My Product.", newProduct.description());
    }

    public void testNewProductWithDiscussion() throws Exception {
        ProductOwner productOwner = this.persistedProductOwnerForTest();

        String newProductId =
        		ApplicationServiceRegistry.productApplicationService().newProductWithDiscussion(
                    new NewProductCommand(
                            "T-12345",
                            productOwner.productOwnerId().id(),
                            "My Product",
                            "The description of My Product."));

        Product newProduct =
                DomainRegistry.productRepository()
                    .productOfId(
                            productOwner.tenantId(),
                            new ProductId(newProductId));

        assertNotNull(newProduct);
        assertEquals("My Product", newProduct.name());
        assertEquals("The description of My Product.", newProduct.description());
        assertEquals(DiscussionAvailability.REQUESTED, newProduct.discussion().availability());
    }

    public void testRequestProductDiscussion() throws Exception {
        Product product = this.persistedProductForTest();

        ApplicationServiceRegistry.productApplicationService().requestProductDiscussion(
                new RequestProductDiscussionCommand(
                        product.tenantId().id(),
                        product.productId().id()));

        Product productWithRequestedDiscussion =
                DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertEquals(DiscussionAvailability.REQUESTED, productWithRequestedDiscussion.discussion().availability());
    }

    public void testRetryProductDiscussionRequest() throws Exception {
        Product product = this.persistedProductForTest();

        ApplicationServiceRegistry.productApplicationService().requestProductDiscussion(
                new RequestProductDiscussionCommand(
                        product.tenantId().id(),
                        product.productId().id()));

        Product productWithRequestedDiscussion =
        		DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertEquals(DiscussionAvailability.REQUESTED, productWithRequestedDiscussion.discussion().availability());

        ApplicationServiceRegistry.productApplicationService().startDiscussionInitiation(
                new StartDiscussionInitiationCommand(
                        product.tenantId().id(),
                        product.productId().id()));

        Product productWithDiscussionInitiation =
        		DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertNotNull(productWithDiscussionInitiation.discussionInitiationId());

        ApplicationServiceRegistry.productApplicationService().retryProductDiscussionRequest(
                new RetryProductDiscussionRequestCommand(
                        product.tenantId().id(),
                        productWithDiscussionInitiation.discussionInitiationId()));

        Product productWithRetriedRequestedDiscussion =
        		DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertEquals(DiscussionAvailability.REQUESTED, productWithRetriedRequestedDiscussion.discussion().availability());
    }

    public void testStartDiscussionInitiation() throws Exception {
        Product product = this.persistedProductForTest();

        ApplicationServiceRegistry.productApplicationService().requestProductDiscussion(
                new RequestProductDiscussionCommand(
                        product.tenantId().id(),
                        product.productId().id()));

        Product productWithRequestedDiscussion =
        		DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertEquals(DiscussionAvailability.REQUESTED, productWithRequestedDiscussion.discussion().availability());

        assertNull(productWithRequestedDiscussion.discussionInitiationId());

        ApplicationServiceRegistry.productApplicationService().startDiscussionInitiation(
                new StartDiscussionInitiationCommand(
                        product.tenantId().id(),
                        product.productId().id()));

        Product productWithDiscussionInitiation =
        		DomainRegistry.productRepository()
                    .productOfId(
                            product.tenantId(),
                            product.productId());

        assertNotNull(productWithDiscussionInitiation.discussionInitiationId());
    }

    public void testTimeOutProductDiscussionRequest() throws Exception {
        // TODO: student assignment
    }
}
