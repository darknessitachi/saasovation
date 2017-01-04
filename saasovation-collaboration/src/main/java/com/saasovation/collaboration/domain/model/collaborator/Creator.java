package com.saasovation.collaboration.domain.model.collaborator;

public final class Creator extends Collaborator {

    private static final long serialVersionUID = 1L;

    public Creator(String anIdentity, String aName, String anEmailAddress) {
        super(anIdentity, aName, anEmailAddress);
    }

    protected Creator() {
        super();
    }

    @Override
    protected int hashPrimeValue() {
        return 43;
    }
}
