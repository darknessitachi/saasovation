package org.infinite.identityaccess.domain.model.identity;

import java.io.Serializable;

import com.abigdreamer.infinity.common.lang.AssertionConcern;


/**
 * 联系信息
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:17:59
 * @version V1.0
 */
public final class ContactInformation extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private EmailAddress emailAddress;// 邮件地址
    private PostalAddress postalAddress;// 邮政地址
    private Telephone primaryTelephone;// 主号码
    private Telephone secondaryTelephone;// 副号码

    public ContactInformation(
            EmailAddress anEmailAddress,
            PostalAddress aPostalAddress,
            Telephone aPrimaryTelephone,
            Telephone aSecondaryTelephone) {

        super();

        this.setEmailAddress(anEmailAddress);
        this.setPostalAddress(aPostalAddress);
        this.setPrimaryTelephone(aPrimaryTelephone);
        this.setSecondaryTelephone(aSecondaryTelephone);
    }

    public ContactInformation(ContactInformation aContactInformation) {
        this(aContactInformation.emailAddress(),
             aContactInformation.postalAddress(),
             aContactInformation.primaryTelephone(),
             aContactInformation.secondaryTelephone());
    }

    public ContactInformation changeEmailAddress(EmailAddress anEmailAddress) {
        return new ContactInformation(
                anEmailAddress,
                this.postalAddress(),
                this.primaryTelephone(),
                this.secondaryTelephone());
    }

    public ContactInformation changePostalAddress(PostalAddress aPostalAddress) {
        return new ContactInformation(
               this.emailAddress(),
               aPostalAddress,
               this.primaryTelephone(),
               this.secondaryTelephone());
    }

    public ContactInformation changePrimaryTelephone(Telephone aTelephone) {
        return new ContactInformation(
               this.emailAddress(),
               this.postalAddress(),
               aTelephone,
               this.secondaryTelephone());
    }

    public ContactInformation changeSecondaryTelephone(Telephone aTelephone) {
        return new ContactInformation(
               this.emailAddress(),
               this.postalAddress(),
               this.primaryTelephone(),
               aTelephone);
    }

    public EmailAddress emailAddress() {
        return this.emailAddress;
    }

    public PostalAddress postalAddress() {
        return this.postalAddress;
    }

    public Telephone primaryTelephone() {
        return this.primaryTelephone;
    }

    public Telephone secondaryTelephone() {
        return this.secondaryTelephone;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            ContactInformation typedObject = (ContactInformation) anObject;
            equalObjects =
                this.emailAddress().equals(typedObject.emailAddress()) &&
                this.postalAddress().equals(typedObject.postalAddress()) &&
                this.primaryTelephone().equals(typedObject.primaryTelephone()) &&
                ((this.secondaryTelephone() == null && typedObject.secondaryTelephone() == null) ||
                 (this.secondaryTelephone() != null && this.secondaryTelephone().equals(typedObject.secondaryTelephone())));
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (73213 * 173)
            + this.emailAddress().hashCode()
            + this.postalAddress().hashCode()
            + this.primaryTelephone().hashCode()
            + (this.secondaryTelephone() == null ? 0:this.secondaryTelephone().hashCode());

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "ContactInformation [emailAddress=" + emailAddress + ", postalAddress=" + postalAddress + ", primaryTelephone="
                + primaryTelephone + ", secondaryTelephone=" + secondaryTelephone + "]";
    }

    protected ContactInformation() {
        super();
    }

    private void setEmailAddress(EmailAddress anEmailAddress) {
        this.assertArgumentNotNull(anEmailAddress, "The email address is required.");

        this.emailAddress = anEmailAddress;
    }

    private void setPostalAddress(PostalAddress aPostalAddress) {
        this.assertArgumentNotNull(aPostalAddress, "The postal address is required.");

        this.postalAddress = aPostalAddress;
    }

    private void setPrimaryTelephone(Telephone aPrimaryTelephone) {
        this.assertArgumentNotNull(aPrimaryTelephone, "The primary telephone is required.");

        this.primaryTelephone = aPrimaryTelephone;
    }

    private void setSecondaryTelephone(Telephone aSecondaryTelephone) {
        this.secondaryTelephone = aSecondaryTelephone;
    }
}
