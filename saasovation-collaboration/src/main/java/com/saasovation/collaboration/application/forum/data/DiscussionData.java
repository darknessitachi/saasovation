package com.saasovation.collaboration.application.forum.data;

/**
 * 讨论数据
 * 
 * @author Darkness
 * @date 2014-5-31 下午2:11:10
 * @version V1.0
 */
public class DiscussionData {

    private String authorEmailAddress;
    private String authorIdentity;
    private String authorName;
    private boolean closed;
    private String discussionId;
    private String exclusiveOwner;
    private String forumId;
    private String subject;
    private String tenantId;

    public DiscussionData() {
        super();
    }

    public String getAuthorEmailAddress() {
        return this.authorEmailAddress;
    }

    public void setAuthorEmailAddress(String authorEmailAddress) {
        this.authorEmailAddress = authorEmailAddress;
    }

    public String getAuthorIdentity() {
        return this.authorIdentity;
    }

    public void setAuthorIdentity(String authorIdentity) {
        this.authorIdentity = authorIdentity;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getDiscussionId() {
        return this.discussionId;
    }

    public void setDiscussionId(String discussionId) {
        this.discussionId = discussionId;
    }

    public String getExclusiveOwner() {
        return this.exclusiveOwner;
    }

    public void setExclusiveOwner(String exclusiveOwner) {
        this.exclusiveOwner = exclusiveOwner;
    }

    public String getForumId() {
        return this.forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
