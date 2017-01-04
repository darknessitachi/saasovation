package com.saasovation.collaboration.domain.model.collaborator;

public class Owner extends Collaborator {

    private static final long serialVersionUID = 1L;

    public Owner(String anIdentity, String aName, String anEmailAddress) {
        super(anIdentity, aName, anEmailAddress);
    }

    protected Owner() {
        super();
    }

    @Override
    protected int hashPrimeValue() {
        return 29;
    }
}
