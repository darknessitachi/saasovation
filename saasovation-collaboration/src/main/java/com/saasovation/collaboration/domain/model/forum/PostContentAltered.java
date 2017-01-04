package com.saasovation.collaboration.domain.model.forum;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 修改提交内容
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:46:44
 * @version V1.0
 */
public class PostContentAltered implements DomainEvent {

    private String bodyText;
    private DiscussionId discussionId;
    private int eventVersion;
    private ForumId forumId;
    private Date occurredOn;
    private PostId postId;
    private String subject;
    private Tenant tenant;

    public PostContentAltered(
            Tenant aTenant,
            ForumId aForumId,
            DiscussionId aDiscussionId,
            PostId aPostId,
            String aSubject,
            String aBodyText) {

        super();

        this.bodyText = aBodyText;
        this.discussionId = aDiscussionId;
        this.eventVersion = 1;
        this.forumId = aForumId;
        this.occurredOn = new Date();
        this.postId = aPostId;
        this.subject = aSubject;
        this.tenant = aTenant;
    }

    public String bodyText() {
        return this.bodyText;
    }

    public DiscussionId discussionId() {
        return this.discussionId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    public ForumId forumId() {
        return this.forumId;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public PostId postId() {
        return this.postId;
    }

    public String subject() {
        return this.subject;
    }

    public Tenant tenant() {
        return this.tenant;
    }
}
