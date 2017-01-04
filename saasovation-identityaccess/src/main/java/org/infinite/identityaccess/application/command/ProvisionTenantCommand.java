package org.infinite.identityaccess.application.command;

/**
 * 准备租赁命令
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:18:01
 * @version V1.0
 */
public class ProvisionTenantCommand {

    private String tenantName;
    private String tenantDescription;
    private String administorFirstName;
    private String administorLastName;
    private String emailAddress;
    private String primaryTelephone;
    private String secondaryTelephone;
    private String addressStreetAddress;
    private String addressCity;
    private String addressStateProvince;
    private String addressPostalCode;
    private String addressCountryCode;

    public ProvisionTenantCommand(String tenantName, String tenantDescription, String administorFirstName,
            String administorLastName, String emailAddress, String primaryTelephone, String secondaryTelephone,
            String addressStreetAddress, String addressCity, String addressStateProvince, String addressPostalCode,
            String addressCountryCode) {

        super();

        this.tenantName = tenantName;
        this.tenantDescription = tenantDescription;
        this.administorFirstName = administorFirstName;
        this.administorLastName = administorLastName;
        this.emailAddress = emailAddress;
        this.primaryTelephone = primaryTelephone;
        this.secondaryTelephone = secondaryTelephone;
        this.addressStreetAddress = addressStreetAddress;
        this.addressCity = addressCity;
        this.addressStateProvince = addressStateProvince;
        this.addressPostalCode = addressPostalCode;
        this.addressCountryCode = addressCountryCode;
    }

    public ProvisionTenantCommand() {
        super();
    }

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantDescription() {
        return this.tenantDescription;
    }

    public void setTenantDescription(String tenantDescription) {
        this.tenantDescription = tenantDescription;
    }

    public String getAdministorFirstName() {
        return this.administorFirstName;
    }

    public void setAdministorFirstName(String administorFirstName) {
        this.administorFirstName = administorFirstName;
    }

    public String getAdministorLastName() {
        return this.administorLastName;
    }

    public void setAdministorLastName(String administorLastName) {
        this.administorLastName = administorLastName;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPrimaryTelephone() {
        return this.primaryTelephone;
    }

    public void setPrimaryTelephone(String primaryTelephone) {
        this.primaryTelephone = primaryTelephone;
    }

    public String getSecondaryTelephone() {
        return this.secondaryTelephone;
    }

    public void setSecondaryTelephone(String secondaryTelephone) {
        this.secondaryTelephone = secondaryTelephone;
    }

    public String getAddressStreetAddress() {
        return this.addressStreetAddress;
    }

    public void setAddressStreetAddress(String addressStreetAddress) {
        this.addressStreetAddress = addressStreetAddress;
    }

    public String getAddressCity() {
        return this.addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStateProvince() {
        return this.addressStateProvince;
    }

    public void setAddressStateProvince(String addressStateProvince) {
        this.addressStateProvince = addressStateProvince;
    }

    public String getAddressPostalCode() {
        return this.addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressCountryCode() {
        return this.addressCountryCode;
    }

    public void setAddressCountryCode(String addressCountryCode) {
        this.addressCountryCode = addressCountryCode;
    }
}
