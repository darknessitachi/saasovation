package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionDescriptor;

public class BacklogItemDiscussion extends ValueObject {

    private DiscussionAvailability availability;
    private DiscussionDescriptor descriptor;

    public static BacklogItemDiscussion fromAvailability(
            DiscussionAvailability anAvailability) {

        if (anAvailability.isReady()) {
            throw new IllegalArgumentException("Cannot be created ready.");
        }

        DiscussionDescriptor descriptor =
                new DiscussionDescriptor(DiscussionDescriptor.UNDEFINED_ID);

        return new BacklogItemDiscussion(descriptor, anAvailability);
    }

    public BacklogItemDiscussion(
            DiscussionDescriptor aDescriptor,
            DiscussionAvailability anAvailability) {

        this();

        this.setAvailability(anAvailability);
        this.setDescriptor(aDescriptor);
    }

    public BacklogItemDiscussion(BacklogItemDiscussion aBacklogItemDiscussion) {
        this(aBacklogItemDiscussion.descriptor(), aBacklogItemDiscussion.availability());
    }

    public DiscussionAvailability availability() {
        return this.availability;
    }

    public DiscussionDescriptor descriptor() {
        return this.descriptor;
    }

    public BacklogItemDiscussion nowReady(DiscussionDescriptor aDescriptor) {
        if (aDescriptor == null || aDescriptor.isUndefined()) {
            throw new IllegalStateException("The discussion descriptor must be defined.");
        }
        if (!this.availability().isRequested()) {
            throw new IllegalStateException("The discussion must be requested first.");
        }

        return new BacklogItemDiscussion(aDescriptor, DiscussionAvailability.READY);
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            BacklogItemDiscussion typedObject = (BacklogItemDiscussion) anObject;
            equalObjects =
                    this.availability().equals(typedObject.availability()) &&
                    this.descriptor().equals(typedObject.descriptor());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (5327 * 11)
            + this.availability().hashCode()
            + this.descriptor().hashCode();

        return hashCodeValue;
    }

    private BacklogItemDiscussion() {
        super();
    }

    private void setAvailability(DiscussionAvailability anAvailability) {
        this.assertArgumentNotNull(anAvailability, "The availability must be provided.");

        this.availability = anAvailability;
    }

    private void setDescriptor(DiscussionDescriptor aDescriptor) {
        this.assertArgumentNotNull(aDescriptor, "The descriptor must be provided.");

        this.descriptor = aDescriptor;
    }
}
