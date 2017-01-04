package com.saasovation.collaboration.domain.model.forum;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 论坛关闭完毕
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:42:33
 * @version V1.0
 */
public class ForumClosed implements DomainEvent {

    private int eventVersion;
    private String exclusiveOwner;
    private ForumId forumId;
    private Date occurredOn;
    private Tenant tenant;

    public ForumClosed(Tenant aTenant, ForumId aForumId, String anExclusiveOwner) {
        super();

        this.eventVersion = 1;
        this.exclusiveOwner = anExclusiveOwner;
        this.forumId = aForumId;
        this.occurredOn = new Date();
        this.tenant = aTenant;
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
