package com.saasovation.collaboration.domain.model.forum;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

import com.saasovation.collaboration.domain.model.collaborator.Creator;
import com.saasovation.collaboration.domain.model.collaborator.Moderator;
import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 论坛启动完毕
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:45:12
 * @version V1.0
 */
public class ForumStarted implements DomainEvent {

    private Creator creator;
    private String description;
    private int eventVersion;
    private String exclusiveOwner;
    private ForumId forumId;
    private Moderator moderator;
    private Date occurredOn;
    private String subject;
    private Tenant tenant;

    public ForumStarted(
            Tenant aTenant,
            ForumId aForumId,
            Creator aCreator,
            Moderator aModerator,
            String aSubject,
            String aDescription,
            String anExclusiveOwner) {

        super();

        this.creator = aCreator;
        this.description = aDescription;
        this.eventVersion = 1;
        this.exclusiveOwner = anExclusiveOwner;
        this.forumId = aForumId;
        this.moderator = aModerator;
        this.occurredOn = new Date();
        this.subject = aSubject;
        this.tenant = aTenant;
    }

    public Creator creator() {
        return this.creator;
    }

    public String description() {
        return this.description;
    }

    public String exclusiveOwner() {
        return this.exclusiveOwner;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
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

    public String subject() {
        return this.subject;
    }

    public Tenant tenant() {
        return this.tenant;
    }
}
