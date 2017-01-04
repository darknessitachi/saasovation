package com.saasovation.collaboration.domain.model.forum;

import com.abigdreamer.infinity.ddd.domain.model.AbstractId;

/**
 * 提交Id
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:48:05
 * @version V1.0
 */
public final class PostId extends AbstractId {

    private static final long serialVersionUID = 1L;

    public PostId(String anId) {
        super(anId);
    }

    protected PostId() {
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
