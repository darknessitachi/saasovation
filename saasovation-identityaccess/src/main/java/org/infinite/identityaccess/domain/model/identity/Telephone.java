package org.infinite.identityaccess.domain.model.identity;

import java.io.Serializable;

import com.rapidark.framework.commons.lang.AssertionConcern;


/**
 * 电话号码
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:37:35
 * @version V1.0
 */
public final class Telephone extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private String number;

    public Telephone(String aNumber) {
        this();

        this.setNumber(aNumber);
    }

    public Telephone(Telephone aTelephone) {
        this(aTelephone.number());
    }

    public String number() {
        return this.number;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Telephone typedObject = (Telephone) anObject;
            equalObjects = this.number().equals(typedObject.number());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (35137 * 239)
            + this.number().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Telephone [number=" + number + "]";
    }

    public Telephone() {
        super();
    }

    private void setNumber(String aNumber) {
        this.assertArgumentNotEmpty(aNumber, "Telephone number is required.");
//        this.assertArgumentLength(aNumber, 5, 20, "Telephone number may not be more than 20 characters.");
//        this.assertArgumentTrue(
//                Pattern.matches("((\\(\\d{3}\\))|(\\d{3}-))\\d{3}-\\d{4}", aNumber),
//                "Telephone number or its format is invalid.");

        this.number = aNumber;
    }
}
