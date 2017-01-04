package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Date;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;


public class MemberChangeTracker extends ValueObject {

    private Date emailAddressChangedOn;
    private Date enablingOn;
    private Date nameChangedOn;

    public boolean canChangeEmailAddress(Date asOfDate) {
        return this.emailAddressChangedOn().before(asOfDate);
    }

    public boolean canChangeName(Date asOfDate) {
        return this.nameChangedOn().before(asOfDate);
    }

    public boolean canToggleEnabling(Date asOfDate) {
        return this.enablingOn().before(asOfDate);
    }

    public MemberChangeTracker emailAddressChangedOn(Date asOfDate) {
        return new MemberChangeTracker(this.enablingOn(), this.nameChangedOn(), asOfDate);
    }

    public MemberChangeTracker enablingOn(Date asOfDate) {
        return new MemberChangeTracker(asOfDate, this.nameChangedOn(), this.emailAddressChangedOn());
    }

    public MemberChangeTracker nameChangedOn(Date asOfDate) {
        return new MemberChangeTracker(this.enablingOn(), asOfDate, this.emailAddressChangedOn());
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            MemberChangeTracker typedObject = (MemberChangeTracker) anObject;
            equalObjects =
                this.enablingOn().equals(typedObject.enablingOn()) &&
                this.nameChangedOn().equals(typedObject.nameChangedOn()) &&
                this.emailAddressChangedOn().equals(typedObject.emailAddressChangedOn());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (8705 * 67)
            + this.enablingOn().hashCode()
            + this.nameChangedOn().hashCode()
            + this.emailAddressChangedOn().hashCode();

        return hashCodeValue;
    }

    protected MemberChangeTracker(
            Date anEnablingOn,
            Date aNameChangedOn,
            Date anEmailAddressChangedOn) {

        this();

        this.setEmailAddressChangedOn(anEmailAddressChangedOn);
        this.setEnablingOn(anEnablingOn);
        this.setNameChangedOn(aNameChangedOn);
    }

    protected MemberChangeTracker(MemberChangeTracker aMemberChangeTracker) {
        this(aMemberChangeTracker.enablingOn(),
             aMemberChangeTracker.nameChangedOn(),
             aMemberChangeTracker.emailAddressChangedOn());
    }

    private MemberChangeTracker() {
        super();
    }

    private Date emailAddressChangedOn() {
        return this.emailAddressChangedOn;
    }

    private Date enablingOn() {
        return this.enablingOn;
    }

    private Date nameChangedOn() {
        return this.nameChangedOn;
    }

    private void setEmailAddressChangedOn(Date anEmailAddressChangedOn) {
        this.assertArgumentNotNull(anEmailAddressChangedOn, "Email address changed on date must be provided.");

        this.emailAddressChangedOn = anEmailAddressChangedOn;
    }

    private void setEnablingOn(Date anEnablingOn) {
        this.assertArgumentNotNull(anEnablingOn, "Enabling date must be provided.");

        this.enablingOn = anEnablingOn;
    }

    private void setNameChangedOn(Date aNameChangedOn) {
        this.assertArgumentNotNull(aNameChangedOn, "Name changed on date must be provided.");

        this.nameChangedOn = aNameChangedOn;
    }
}
