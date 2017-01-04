package com.abigdreamer.saasovation.agilepm.application.product;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.process.ProcessId;
import com.abigdreamer.infinity.ddd.domain.model.process.TimeConstrainedProcessTracker;
import com.abigdreamer.infinity.ddd.domain.model.process.TimeConstrainedProcessTrackerRepository;
import com.abigdreamer.saasovation.agilepm.application.ApplicationServiceLifeCycle;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionDescriptor;
import com.abigdreamer.saasovation.agilepm.domain.model.product.Product;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductDiscussionRequestTimedOut;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductId;
import com.abigdreamer.saasovation.agilepm.domain.model.product.ProductRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwner;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwnerRepository;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  产品应用服务
 * 
 * @author Darkness
 * @date 2014-5-8 下午9:26:10 
 * @version V1.0
 */
public class ProductApplicationService {

    private TimeConstrainedProcessTrackerRepository processTrackerRepository;
    private ProductOwnerRepository productOwnerRepository;
    private ProductRepository productRepository;

    public ProductApplicationService(
            ProductRepository aProductRepository,
            ProductOwnerRepository aProductOwnerRepository,
            TimeConstrainedProcessTrackerRepository aProcessTrackerRepository) {

        super();

        this.processTrackerRepository = aProcessTrackerRepository;
        this.productOwnerRepository = aProductOwnerRepository;
        this.productRepository = aProductRepository;
    }

    // TODO: additional APIs / student assignment

    public void initiateDiscussion(InitiateDiscussionCommand aCommand) {
        ApplicationServiceLifeCycle.begin();

        try {
            Product product =
                    this.productRepository()
                        .productOfId(
                                new TenantId(aCommand.getTenantId()),
                                new ProductId(aCommand.getProductId()));

            if (product == null) {
                throw new IllegalStateException(
                        "Unknown product of tenant id: " + aCommand.getTenantId()
                        + " and product id: " + aCommand.getProductId());
            }

            product.initiateDiscussion(new DiscussionDescriptor(aCommand.getDiscussionId()));

            this.productRepository().save(product);

            ProcessId processId = ProcessId.existingProcessId(product.discussionInitiationId());

            TimeConstrainedProcessTracker tracker =
                    this.processTrackerRepository()
                        .trackerOfProcessId(aCommand.getTenantId(), processId);

            tracker.completed();

            this.processTrackerRepository().save(tracker);

            ApplicationServiceLifeCycle.success();

        } catch (RuntimeException e) {
            ApplicationServiceLifeCycle.fail(e);
        }
    }

    public String newProduct(NewProductCommand aCommand) {

        return this.newProductWith(
                aCommand.getTenantId(),
                aCommand.getProductOwnerId(),
                aCommand.getName(),
                aCommand.getDescription(),
                DiscussionAvailability.NOT_REQUESTED);
    }

    public String newProductWithDiscussion(NewProductCommand aCommand) {

        return this.newProductWith(
                aCommand.getTenantId(),
                aCommand.getProductOwnerId(),
                aCommand.getName(),
                aCommand.getDescription(),
                this.requestDiscussionIfAvailable());
    }

    public void requestProductDiscussion(RequestProductDiscussionCommand aCommand) {
        Product product =
                this.productRepository()
                    .productOfId(
                            new TenantId(aCommand.getTenantId()),
                            new ProductId(aCommand.getProductId()));

        if (product == null) {
            throw new IllegalStateException(
                    "Unknown product of tenant id: "
                    + aCommand.getTenantId()
                    + " and product id: "
                    + aCommand.getProductId());
        }

        this.requestProductDiscussionFor(product);
    }

    public void retryProductDiscussionRequest(RetryProductDiscussionRequestCommand aCommand) {

        ProcessId processId = ProcessId.existingProcessId(aCommand.getProcessId());

        TenantId tenantId = new TenantId(aCommand.getTenantId());

        Product product =
                this.productRepository()
                    .productOfDiscussionInitiationId(
                            tenantId,
                            processId.id());

        if (product == null) {
            throw new IllegalStateException(
                    "Unknown product of tenant id: "
                    + aCommand.getTenantId()
                    + " and discussion initiation id: "
                    + processId.id());
        }

        this.requestProductDiscussionFor(product);
    }

    public void startDiscussionInitiation(StartDiscussionInitiationCommand aCommand) {

        ApplicationServiceLifeCycle.begin();

        try {
            Product product =
                    this.productRepository()
                        .productOfId(
                                new TenantId(aCommand.getTenantId()),
                                new ProductId(aCommand.getProductId()));

            if (product == null) {
                throw new IllegalStateException(
                        "Unknown product of tenant id: "
                        + aCommand.getTenantId()
                        + " and product id: "
                        + aCommand.getProductId());
            }

            String timedOutEventName =
                    ProductDiscussionRequestTimedOut.class.getName();

            TimeConstrainedProcessTracker tracker =
                    new TimeConstrainedProcessTracker(
                            product.tenantId().id(),
                            ProcessId.newProcessId(),
                            "Create discussion for product: "
                                + product.name(),
                            new Date(),
                            5L * 60L * 1000L, // retries every 5 minutes
                            3, // 3 total retries
                            timedOutEventName);

            this.processTrackerRepository().save(tracker);

            product.startDiscussionInitiation(tracker.processId().id());

            this.productRepository().save(product);

            ApplicationServiceLifeCycle.success();

        } catch (RuntimeException e) {
            ApplicationServiceLifeCycle.fail(e);
        }
    }

    public void timeOutProductDiscussionRequest(TimeOutProductDiscussionRequestCommand aCommand) {

        ApplicationServiceLifeCycle.begin();

        try {
            ProcessId processId = ProcessId.existingProcessId(aCommand.getProcessId());

            TenantId tenantId = new TenantId(aCommand.getTenantId());

            Product product =
                    this.productRepository()
                        .productOfDiscussionInitiationId(
                                tenantId,
                                processId.id());

            this.sendEmailForTimedOutProcess(product);

            product.failDiscussionInitiation();

            this.productRepository().save(product);

            ApplicationServiceLifeCycle.success();

        } catch (RuntimeException e) {
            ApplicationServiceLifeCycle.fail(e);
        }
    }

    private void sendEmailForTimedOutProcess(Product aProduct) {

        // TODO: Implement

    }

    /**
     *  创建产品
     * 
     * @author Darkness
     * @date 2014-5-8 下午9:16:56 
     * @version V1.0
     */
    private String newProductWith(
            String aTenantId,
            String aProductOwnerId,
            String aName,
            String aDescription,
            DiscussionAvailability aDiscussionAvailability) {

        TenantId tenantId = new TenantId(aTenantId);
        ProductId productId = null;

        ApplicationServiceLifeCycle.begin();

        try {
            productId = this.productRepository().nextIdentity();

            // 查询产品负责人
            ProductOwner productOwner =
                    this.productOwnerRepository()
                        .productOwnerOfIdentity(
                                tenantId,
                                aProductOwnerId);

            // 创建产品
            Product product =
                    new Product(
                            tenantId,
                            productId,
                            productOwner.productOwnerId(),
                            aName,
                            aDescription,
                            aDiscussionAvailability);

            // 保存产品
            this.productRepository().save(product);

            ApplicationServiceLifeCycle.success();

        } catch (RuntimeException e) {
            ApplicationServiceLifeCycle.fail(e);
        }

        return productId.id();
    }

    private DiscussionAvailability requestDiscussionIfAvailable() {
        DiscussionAvailability availability = DiscussionAvailability.ADD_ON_NOT_ENABLED;

        boolean enabled = true; // TODO: determine add-on enabled

        if (enabled) {
            availability = DiscussionAvailability.REQUESTED;
        }

        return availability;
    }

    private TimeConstrainedProcessTrackerRepository processTrackerRepository() {
        return this.processTrackerRepository;
    }

    private ProductOwnerRepository productOwnerRepository() {
        return this.productOwnerRepository;
    }

    private ProductRepository productRepository() {
        return this.productRepository;
    }

    private void requestProductDiscussionFor(Product aProduct) {

        ApplicationServiceLifeCycle.begin();

        try {
            aProduct.requestDiscussion(this.requestDiscussionIfAvailable());

            this.productRepository().save(aProduct);

            ApplicationServiceLifeCycle.success();
            
        } catch (RuntimeException e) {
            ApplicationServiceLifeCycle.fail(e);
        }
    }
}
