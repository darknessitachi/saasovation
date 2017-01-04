package com.saasovation.collaboration.domain.model.collaborator;

/**
 *  主持者
 * 
 * @author Darkness
 * @date 2014-5-10 下午2:22:12 
 * @version V1.0
 */
public final class Moderator extends Collaborator {

    private static final long serialVersionUID = 1L;

    public Moderator(String anIdentity, String aName, String anEmailAddress) {
        super(anIdentity, aName, anEmailAddress);
    }

    protected Moderator() {
        super();
    }

    @Override
    protected int hashPrimeValue() {
        return 59;
    }
}
