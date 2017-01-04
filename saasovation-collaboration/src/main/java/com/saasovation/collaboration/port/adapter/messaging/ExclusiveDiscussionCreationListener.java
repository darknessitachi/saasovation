package com.saasovation.collaboration.port.adapter.messaging;

import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.Exchanges;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.RabbitExchangeListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.saasovation.collaboration.application.forum.ForumApplicationService;

public class ExclusiveDiscussionCreationListener implements ExchangeListener 
//extends RabbitExchangeListener 
{

    @Autowired
    private ForumApplicationService forumApplicationService;

    public ExclusiveDiscussionCreationListener() {
        super();
    }

    @Override
    public String exchangeName() {
        return Exchanges.COLLABORATION_EXCHANGE_NAME;
    }

    @Override
    public void filteredDispatch(String aType, String aTextMessage) {
        NotificationReader reader = new NotificationReader(aTextMessage);

        String tenantId = reader.eventStringValue("tenantId");
        String exclusiveOwnerId = reader.eventStringValue("exclusiveOwnerId");
        String creatorId = reader.eventStringValue("creatorId");
        String moderatorId = reader.eventStringValue("moderatorId");
        String authorId = reader.eventStringValue("authorId");
        String forumSubject = reader.eventStringValue("forumTitle");
        String forumDescription = reader.eventStringValue("forumDescription");
        String discussionSubject = reader.eventStringValue("discussionSubject");

        forumApplicationService
            .startExclusiveForumWithDiscussion(
                    tenantId,
                    exclusiveOwnerId,
                    creatorId,
                    moderatorId,
                    authorId,
                    forumSubject,
                    forumDescription,
                    discussionSubject,
                    null);
    }

    @Override
    public String[] listensTo() {
        return new String[] {
                "com.saasovation.collaboration.discussion.CreateExclusiveDiscussion"
            };
    }
    
    @Override
    public String name() {
    	return this.getClass().getName();
    }
}
