package com.abigdreamer.saasovation.agilepm.domain.model.product;

import com.abigdreamer.saasovation.agilepm.domain.model.ValueObject;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionAvailability;
import com.abigdreamer.saasovation.agilepm.domain.model.discussion.DiscussionDescriptor;

/**
 * 产品讨论
 * 
 * @author Darkness
 * @date 2014-5-29 下午3:30:12
 * @version V1.0
 */
public final class ProductDiscussion extends ValueObject {

    private DiscussionAvailability availability;
    private DiscussionDescriptor descriptor;

    public static ProductDiscussion fromAvailability(
            DiscussionAvailability anAvailability) {

        if (anAvailability.isReady()) {
            throw new IllegalArgumentException("Cannot be created ready.");
        }

        DiscussionDescriptor descriptor =
                new DiscussionDescriptor(DiscussionDescriptor.UNDEFINED_ID);

        return new ProductDiscussion(descriptor, anAvailability);
    }

    public ProductDiscussion(
            DiscussionDescriptor aDescriptor,
            DiscussionAvailability anAvailability) {

        super();

        this.setAvailability(anAvailability);
        this.setDescriptor(aDescriptor);
    }

    public ProductDiscussion(ProductDiscussion aProductDiscussion) {
        this(aProductDiscussion.descriptor(), aProductDiscussion.availability());
    }

    public DiscussionAvailability availability() {
        return this.availability;
    }

    public DiscussionDescriptor descriptor() {
        return this.descriptor;
    }

    public ProductDiscussion nowReady(DiscussionDescriptor aDescriptor) {
        if (aDescriptor == null || aDescriptor.isUndefined()) {
            throw new IllegalStateException("The discussion descriptor must be defined.");
        }
        if (!this.availability().isRequested()) {
            throw new IllegalStateException("The discussion must be requested first.");
        }

        return new ProductDiscussion(aDescriptor, DiscussionAvailability.READY);
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
