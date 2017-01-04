package com.saasovation.collaboration.domain.model.collaborator;

/**
 *  参与者
 * 
 * @author Darkness
 * @date 2014-5-10 下午4:28:30 
 * @version V1.0
 */
public final class Participant extends Collaborator {

    private static final long serialVersionUID = 1L;

    public Participant(String anIdentity, String aName, String anEmailAddress) {
        super(anIdentity, aName, anEmailAddress);
    }

    protected Participant() {
        super();
    }

    @Override
    protected int hashPrimeValue() {
        return 23;
    }
}
