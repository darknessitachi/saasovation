package org.infinite.identityaccess.domain.model.identity;

import java.util.UUID;

import com.abigdreamer.infinity.ddd.domain.model.AbstractId;


/**
 * 租赁Id
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:31:27
 * @version V1.0
 */
public final class TenantId extends AbstractId {

    private static final long serialVersionUID = 1L;

    public TenantId(String anId) {
        super(anId);
    }

    protected TenantId() {
        super();
    }

    @Override
    protected int hashOddValue() {
        return 83811;
    }

    @Override
    protected int hashPrimeValue() {
        return 263;
    }

    @Override
    protected void validateId(String anId) {
        try {
            UUID.fromString(anId);
        } catch (Exception e) {
            throw new IllegalArgumentException("The id has an invalid format.");
        }
    }
}
