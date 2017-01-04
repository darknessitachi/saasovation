package com.saasovation.collaboration.domain.model.forum;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;

import com.saasovation.collaboration.domain.model.tenant.Tenant;

/**
 * 论坛描述发生改变
 * 
 * @author Darkness
 * @date 2014-5-30 下午3:42:59
 * @version V1.0
 */
public class ForumDescriptionChanged implements DomainEvent {

    private String description;
    private int eventVersion;
    private String exclusiveOwner;
    private ForumId forumId;
    private Date occurredOn;
    private Tenant tenant;

    public ForumDescriptionChanged(
            Tenant aTenant,
            ForumId aForumId,
            String aDescription,
            String anExclusiveOwner) {

        super();

        this.description = aDescription;
        this.eventVersion = 1;
        this.exclusiveOwner = anExclusiveOwner;
        this.forumId = aForumId;
        this.occurredOn = new Date();
        this.tenant = aTenant;
    }

    public String description() {
        return this.description;
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
