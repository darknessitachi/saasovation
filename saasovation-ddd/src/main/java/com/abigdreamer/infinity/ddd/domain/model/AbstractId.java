package com.abigdreamer.infinity.ddd.domain.model;

import java.io.Serializable;

import com.abigdreamer.infinity.common.lang.AssertionConcern;


/**
 * 抽象的Id，所有Id的基类
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:30:46
 * @version V1.0
 */
public abstract class AbstractId extends AssertionConcern implements Identity, Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            AbstractId typedObject = (AbstractId) anObject;
            equalObjects = this.id().equals(typedObject.id());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                + (this.hashOddValue() * this.hashPrimeValue())
                + this.id().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [id=" + id + "]";
    }

    protected AbstractId(String anId) {
        this();

        this.setId(anId);
    }

    protected AbstractId() {
        super();
    }

    protected abstract int hashOddValue();

    protected abstract int hashPrimeValue();

    protected void validateId(String anId) {
        // implemented by subclasses for validation.
        // throws a runtime exception if invalid.
    }

    private void setId(String anId) {
        this.assertArgumentNotEmpty(anId, "The basic identity is required.");
        this.assertArgumentLength(anId, 36, "The basic identity must be 36 characters.");

        this.validateId(anId);

        this.id = anId;
    }
}
