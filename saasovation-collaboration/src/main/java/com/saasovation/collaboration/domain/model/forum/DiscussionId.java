package com.saasovation.collaboration.domain.model.forum;

import com.abigdreamer.infinity.ddd.domain.model.AbstractId;

/**
 * 讨论Id
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:40:50
 * @version V1.0
 */
public final class DiscussionId extends AbstractId {

    private static final long serialVersionUID = 1L;

    public DiscussionId(String anId) {
        super(anId);
    }

    protected DiscussionId() {
        super();
    }

    @Override
    protected int hashOddValue() {
        return 11735;
    }

    @Override
    protected int hashPrimeValue() {
        return 37;
    }
}
