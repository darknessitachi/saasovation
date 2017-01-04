package com.saasovation.collaboration.domain.model.forum;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

import com.saasovation.collaboration.domain.model.collaborator.Moderator;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 论坛管理者发生改变
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:44:03
 * @version V1.0
 */
public class ForumModeratorChanged implements DomainEvent {

    private int eventVersion;
    private String exclusiveOwner;
    private ForumId forumId;
    private Moderator moderator;
    private Date occurredOn;
    private Tenant tenant;

    public ForumModeratorChanged(
            Tenant aTenant,
            ForumId aForumId,
            Moderator aModerator,
            String anExclusiveOwner) {

        super();

        this.eventVersion = 1;
        this.exclusiveOwner = anExclusiveOwner;
        this.forumId = aForumId;
        this.moderator = aModerator;
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

    public Moderator moderator() {
        return this.moderator;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public Tenant tenant() {
        return this.tenant;
    }
}
