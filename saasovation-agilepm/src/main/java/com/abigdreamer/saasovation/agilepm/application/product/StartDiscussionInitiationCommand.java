package com.abigdreamer.saasovation.agilepm.application.product;

public class StartDiscussionInitiationCommand {

    private String tenantId;
    private String productId;

    public StartDiscussionInitiationCommand(String tenantId, String productId) {
        super();
        this.tenantId = tenantId;
        this.productId = productId;
    }

    public StartDiscussionInitiationCommand() {
        super();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
