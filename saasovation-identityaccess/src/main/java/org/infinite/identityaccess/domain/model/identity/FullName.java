package org.infinite.identityaccess.domain.model.identity;

import java.io.Serializable;
import java.util.regex.Pattern;

import com.abigdreamer.infinity.common.lang.AssertionConcern;


/**
 * 姓名
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:40:14
 * @version V1.0
 */
public final class FullName extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;

    public FullName(String aFirstName, String aLastName) {
        super();

        this.setFirstName(aFirstName);
        this.setLastName(aLastName);
    }

    public FullName(FullName aFullName) {
        this(aFullName.firstName(), aFullName.lastName());
    }

    public String asFormattedName() {
        return this.firstName() + " " + this.lastName();
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public FullName withChangedFirstName(String aFirstName) {
        return new FullName(aFirstName, this.lastName());
    }

    public FullName withChangedLastName(String aLastName) {
        return new FullName(this.firstName(), aLastName);
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            FullName typedObject = (FullName) anObject;
            equalObjects =
                this.firstName().equals(typedObject.firstName()) &&
                this.lastName().equals(typedObject.lastName());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (59151 * 191)
            + this.firstName().hashCode()
            + this.lastName().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "FullName [firstName=" + firstName + ", lastName=" + lastName + "]";
    }

    protected FullName() {
        super();
    }

    private void setFirstName(String aFirstName) {
        this.assertArgumentNotEmpty(aFirstName, "First name is required.");
        this.assertArgumentLength(aFirstName, 1, 50, "First name must be 50 characters or less.");
        this.assertArgumentTrue(
                Pattern.matches("[A-Z][a-z]*", aFirstName),
                "First name must be at least one character in length, starting with a capital letter.");

        this.firstName = aFirstName;
    }

    private void setLastName(String aLastName) {
        this.assertArgumentNotEmpty(aLastName, "The last name is required.");
        this.assertArgumentLength(aLastName, 1, 50, "The last name must be 50 characters or less.");
        this.assertArgumentTrue(
                Pattern.matches("^[a-zA-Z'][ a-zA-Z'-]*[a-zA-Z']?", aLastName),
                "Last name must be at least one character in length.");

        this.lastName = aLastName;
    }
}
