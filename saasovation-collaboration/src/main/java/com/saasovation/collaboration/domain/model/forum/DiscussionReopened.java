package com.saasovation.collaboration.domain.model.forum;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 讨论响应完毕
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:41:27
 * @version V1.0
 */
public class DiscussionReopened implements DomainEvent {

    private DiscussionId discussionId;
    private int eventVersion;
    private String exclusiveOwner;
    private ForumId forumId;
    private Date occurredOn;
    private Tenant tenant;

    public DiscussionReopened(
            Tenant aTenant,
            ForumId aForumId,
            DiscussionId aDiscussionId,
            String anExclusiveOwner) {

        super();

        this.discussionId = aDiscussionId;
        this.eventVersion = 1;
        this.exclusiveOwner = anExclusiveOwner;
        this.forumId = aForumId;
        this.occurredOn = new Date();
        this.tenant = aTenant;
    }

    public DiscussionId discussionId() {
        return this.discussionId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public String exclusiveOwner() {
        return this.exclusiveOwner;
    }

    public ForumId forumId() {
        return this.forumId;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public Tenant tenant() {
        return this.tenant;
    }
}
