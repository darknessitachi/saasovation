package com.saasovation.collaboration.domain.model.forum;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

import com.saasovation.collaboration.domain.model.collaborator.Author;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 提交至讨论
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:47:13
 * @version V1.0
 */
public class PostedToDiscussion implements DomainEvent {

    private Author author;
    private String bodyText;
    private DiscussionId discussionId;
    private int eventVersion;
    private ForumId forumId;
    private Date occurredOn;
    private PostId postId;
    private PostId replyToPost;
    private String subject;
    private Tenant tenant;

    public PostedToDiscussion(
            Tenant aTenant,
            ForumId aForumId,
            DiscussionId aDiscussionId,
            PostId aReplyToPostId,
            PostId aPostId,
            Author anAuthor,
            String aSubject,
            String aBodyText) {

        super();

        this.author = anAuthor;
        this.bodyText = aBodyText;
        this.discussionId = aDiscussionId;
        this.eventVersion = 1;
        this.forumId = aForumId;
        this.occurredOn = new Date();
        this.postId = aPostId;
        this.replyToPost = aReplyToPostId;
        this.subject = aSubject;
        this.tenant = aTenant;
    }

    public Author author() {
        return this.author;
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

    public PostId replyToPost() {
        return this.replyToPost;
    }

    public String subject() {
        return this.subject;
    }

    public Tenant tenant() {
        return this.tenant;
    }
}
