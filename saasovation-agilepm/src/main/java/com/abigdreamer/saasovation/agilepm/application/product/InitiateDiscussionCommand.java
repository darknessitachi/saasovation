package com.abigdreamer.saasovation.agilepm.application.product;

/**
 *  初始化产品讨论命令
 * 
 * @author Darkness
 * @date 2014-5-8 下午6:12:38 
 * @version V1.0
 */
public class InitiateDiscussionCommand {

    private String tenantId;
    private String productId;// 产品Id
    private String discussionId;// 讨论Id

    public InitiateDiscussionCommand(String tenantId, String productId, String discussionId) {
        super();

        this.tenantId = tenantId;
        this.productId = productId;
        this.discussionId = discussionId;
    }

    public InitiateDiscussionCommand() {
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

    public String getDiscussionId() {
        return discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }
}
