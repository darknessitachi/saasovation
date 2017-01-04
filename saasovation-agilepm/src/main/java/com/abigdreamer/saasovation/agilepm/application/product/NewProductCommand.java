package com.abigdreamer.saasovation.agilepm.application.product;

/**
 *  新建产品命令
 * 
 * @author Darkness
 * @date 2014-5-8 下午6:11:37 
 * @version V1.0
 */
public class NewProductCommand {

    private String tenantId;
    private String productOwnerId;// 产品拥有人ID
    private String name;// 产品名称
    private String description;// 产品描述

    public NewProductCommand(String tenantId, String productOwnerId, String name, String description) {
        super();

        this.tenantId = tenantId;
        this.productOwnerId = productOwnerId;
        this.name = name;
        this.description = description;
    }

    public NewProductCommand() {
        super();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProductOwnerId() {
        return productOwnerId;
    }

    public void setProductOwnerId(String productOwnerId) {
        this.productOwnerId = productOwnerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
