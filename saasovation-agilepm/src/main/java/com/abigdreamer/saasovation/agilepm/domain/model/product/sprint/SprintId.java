package com.abigdreamer.saasovation.agilepm.domain.model.product.sprint;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;
import com.abigdreamer.saasovation.agilepm.domain.model.product.release.ReleaseId;

public class SprintId extends ValueObject {

    private String id;

    public SprintId(String anId) {
        super();

        this.setId(anId);
    }

    public SprintId(ReleaseId aReleaseId) {
        this(aReleaseId.id());
    }

    public String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            SprintId typedObject = (SprintId) anObject;
            equalObjects = this.id().equals(typedObject.id());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (38313 * 43)
            + this.id().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "SprintId [id=" + id + "]";
    }

    protected SprintId() {
        super();
    }

    private void setId(String anId) {
        this.assertArgumentNotEmpty(anId, "The id must be provided.");
        this.assertArgumentLength(anId, 36, "The id must be 36 characters or less.");

        this.id = anId;
    }
}
