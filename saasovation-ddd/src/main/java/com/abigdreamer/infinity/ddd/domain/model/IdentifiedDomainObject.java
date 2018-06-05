package com.abigdreamer.infinity.ddd.domain.model;

import java.io.Serializable;

import com.rapidark.framework.commons.lang.AssertionConcern;


/**
 * 带身份标识的领域对象
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:25:13
 * @version V1.0
 */
public class IdentifiedDomainObject extends AssertionConcern implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    protected IdentifiedDomainObject() {
        super();

        this.setId(-1);
    }

    protected long id() {
        return this.id;
    }

    private void setId(long anId) {
        this.id = anId;
    }
}
