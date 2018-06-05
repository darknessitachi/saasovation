package org.infinite.identityaccess.domain.model.identity;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.rapidark.framework.commons.lang.AssertionConcern;


/**
 * 邮件地址
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:18:21
 * @version V1.0
 */
public final class EmailAddress extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private String address;

    public EmailAddress(String anAddress) {
        super();

        this.setAddress(anAddress);
    }

    public EmailAddress(EmailAddress anEmailAddress) {
        this(anEmailAddress.address());
    }

    public String address() {
        return this.address;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            EmailAddress typedObject = (EmailAddress) anObject;
            equalObjects = this.address().equals(typedObject.address());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (17861 * 179)
            + this.address().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "EmailAddress [address=" + address + "]";
    }

    protected EmailAddress() {
        super();
    }

    private void setAddress(String anAddress) {
        this.assertArgumentNotEmpty(anAddress, "The email address is required.");
        this.assertArgumentLength(anAddress, 1, 100, "Email address must be 100 characters or less.");
        this.assertArgumentTrue(
                Pattern.matches("\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*", anAddress),
                "Email address format is invalid.");

        this.address = anAddress;
    }
}
