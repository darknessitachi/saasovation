package com.saasovation.collaboration.domain.model.tenant;

import com.abigdreamer.infinity.ddd.domain.model.AbstractId;

/**
 * 租赁
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:40:06
 * @version V1.0
 */
public final class Tenant extends AbstractId {

    private static final long serialVersionUID = 1L;

    public Tenant(String anId) {
        super(anId);
    }

    protected Tenant() {
        super();
    }

    @Override
    protected int hashOddValue() {
        return 3517;
    }

    @Override
    protected int hashPrimeValue() {
        return 7;
    }
}
