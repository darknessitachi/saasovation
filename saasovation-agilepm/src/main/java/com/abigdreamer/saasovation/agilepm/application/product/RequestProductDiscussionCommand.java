package com.abigdreamer.saasovation.agilepm.application.product;

public class RequestProductDiscussionCommand {

    private String tenantId;
    private String productId;

    public RequestProductDiscussionCommand(String tenantId, String productId) {
        super();
        this.tenantId = tenantId;
        this.productId = productId;
    }

    public RequestProductDiscussionCommand() {
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
