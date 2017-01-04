package com.saasovation.collaboration.domain.model.collaborator;

/**
 *  作者
 * 
 * @author Darkness
 * @date 2014-5-10 下午2:55:52 
 * @version V1.0
 */
public final class Author extends Collaborator {

    private static final long serialVersionUID = 1L;

    public Author(String anIdentity, String aName, String anEmailAddress) {
        super(anIdentity, aName, anEmailAddress);
    }

    protected Author() {
        super();
    }

    @Override
    protected int hashPrimeValue() {
        return 19;
    }
}
