//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.abigdreamer.saasovation.agilepm.port.adapter.messaging.rabbitmq;

import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.Exchanges;
import com.abigdreamer.saasovation.agilepm.application.product.InitiateDiscussionCommand;
import com.abigdreamer.saasovation.agilepm.application.product.ProductApplicationService;
import com.abigdreamer.saasovation.agilepm.port.adapter.messaging.ProductDiscussionExclusiveOwnerId;


public class RabbitMQDiscussionStartedListener implements ExchangeListener {

    private ProductApplicationService productApplicationService;

    public RabbitMQDiscussionStartedListener(
            ProductApplicationService aProductApplicationService) {

        super();

        this.productApplicationService = aProductApplicationService;
    }

    @Override
    public String exchangeName() {
        return Exchanges.COLLABORATION_EXCHANGE_NAME;
    }

    @Override
    public void filteredDispatch(String aType, String aTextMessage) {
        NotificationReader reader = new NotificationReader(aTextMessage);

        String ownerId = reader.eventStringValue("exclusiveOwner");

        if (!ProductDiscussionExclusiveOwnerId.isValid(ownerId)) {
            return;
        }

        String tenantId = reader.eventStringValue("tenant.id");
        String productId =
                ProductDiscussionExclusiveOwnerId
                    .fromEncodedId(ownerId)
                    .id();
        String discussionId = reader.eventStringValue("discussionId.id");

        this.productApplicationService().initiateDiscussion(
                new InitiateDiscussionCommand(
                    tenantId,
                    productId,
                    discussionId));
    }

    @Override
    public String[] listensTo() {
        return new String[] {
                "com.saasovation.collaboration.domain.model.forum.DiscussionStarted"
                };
    }

    @Override
    public String name() {
    	return this.getClass().getName();
    }
    
    private ProductApplicationService productApplicationService() {
        return this.productApplicationService;
    }
}
