package com.saasovation.collaboration.domain.model.collaborator;

import java.io.Serializable;

/**
 *  协作者
 * 
 * @author Darkness
 * @date 2014-5-10 下午2:55:29 
 * @version V1.0
 */
public abstract class Collaborator implements Comparable<Collaborator>, Serializable {

    private static final long serialVersionUID = 1L;

    private String emailAddress;
    private String identity;
    private String name;

    public Collaborator(String anIdentity, String aName, String anEmailAddress) {
        this();

        this.setEmailAddress(anEmailAddress);
        this.setIdentity(anIdentity);
        this.setName(aName);
    }

    public String emailAddress() {
        return this.emailAddress;
    }

    public String identity() {
        return this.identity;
    }

    public String name() {
        return this.name;
    }

    @Override
    public int compareTo(Collaborator aCollaborator) {

        int diff = this.identity().compareTo(aCollaborator.identity());

        if (diff == 0) {
            diff = this.emailAddress().compareTo(aCollaborator.emailAddress());

            if (diff == 0) {
                diff = this.name().compareTo(aCollaborator.name());
            }
        }

        return diff;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Collaborator typedObject = (Collaborator) anObject;
            equalObjects =
                this.emailAddress().equals(typedObject.emailAddress()) &&
                this.identity().equals(typedObject.identity()) &&
                this.name().equals(typedObject.name());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (57691 * this.hashPrimeValue())
            + this.emailAddress().hashCode()
            + this.identity().hashCode()
            + this.name().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                " [emailAddress=" + emailAddress + ", identity=" + identity + ", name=" + name + "]";
    }

    protected Collaborator() {
        super();
    }

    protected abstract int hashPrimeValue();

    private void setEmailAddress(String anEmailAddress) {
        this.emailAddress = anEmailAddress;
    }

    private void setIdentity(String anIdentity) {
        this.identity = anIdentity;
    }

    private void setName(String aName) {
        this.name = aName;
    }
}
