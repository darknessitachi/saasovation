package com.abigdreamer.infinity.ddd.domain.model;

/**
 * 带标识的值对象基类，标记性基类
 *  
 * @author Darkness
 * @date 2014-11-26 下午9:31:32
 * @version V1.0
 * @since ark 1.0
 */
public class IdentifiedValueObject extends IdentifiedDomainObject {

    private static final long serialVersionUID = 1L;

    protected IdentifiedValueObject() {
        super();
    }
}
