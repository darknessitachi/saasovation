package com.saasovation.collaboration.domain.model.forum;

import com.abigdreamer.infinity.ddd.domain.model.AbstractId;

/**
 * 论坛Id
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:43:20
 * @version V1.0
 */
public final class ForumId extends AbstractId {

    private static final long serialVersionUID = 1L;

    public ForumId(String anId) {
        super(anId);
    }

    protected ForumId() {
        super();
    }

    @Override
    protected int hashOddValue() {
        return 83713;
    }

    @Override
    protected int hashPrimeValue() {
        return 11;
    }
}
