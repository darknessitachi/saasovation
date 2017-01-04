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

import java.util.Date;
import java.util.Properties;

import com.abigdreamer.infinity.common.serializer.PropertiesSerializer;
import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.Exchanges;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.ConnectionSettings;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.Exchange;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.MessageParameters;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.rabbitmq.MessageProducer;
import com.abigdreamer.saasovation.agilepm.application.product.ProductApplicationService;
import com.abigdreamer.saasovation.agilepm.application.product.StartDiscussionInitiationCommand;
import com.abigdreamer.saasovation.agilepm.port.adapter.messaging.ProductDiscussionExclusiveOwnerId;


public class RabbitMQProductDiscussionRequestedListener
implements ExchangeListener {

    private static final String COMMAND =
            "com.saasovation.collaboration.discussion.CreateExclusiveDiscussion";

    private ProductApplicationService productApplicationService;

    protected RabbitMQProductDiscussionRequestedListener(
            ProductApplicationService aProductApplicationService) {

        super();

        this.productApplicationService = aProductApplicationService;
    }

    @Override
    public String exchangeName() {
        return Exchanges.AGILEPM_EXCHANGE_NAME;
    }

    @Override
    public void filteredDispatch(String aType, String aTextMessage) {
        NotificationReader reader = new NotificationReader(aTextMessage);

        if (!reader.eventBooleanValue("requestingDiscussion")) {
            return;
        }

        String tenantId = reader.eventStringValue("tenantId.id");
        String productId = reader.eventStringValue("product.id");

        this.productApplicationService.startDiscussionInitiation(
                new StartDiscussionInitiationCommand(
                        tenantId,
                        productId));

        Properties parameters = this.parametersFrom(reader);
        PropertiesSerializer serializer = PropertiesSerializer.instance();
        String serialization = serializer.serialize(parameters);
        String commandId = this.commandIdFrom(parameters);

        this.messageProducer()
            .send(
                serialization,
                MessageParameters
                    .durableTextParameters(
                            COMMAND,
                            commandId,
                            new Date()))
            .close();
    }

    @Override
    public String[] listensTo() {
        return new String[] {
                "com.saasovation.agilepm.domain.model.product.ProductCreated",
                "com.saasovation.agilepm.domain.model.product.ProductDiscussionRequested"
                };
    }

    @Override
    public String name() {
    	return this.getClass().getName();
    }
    
    private String commandIdFrom(Properties aProperties) {
        String commandId =
                aProperties.getProperty("tenantId")
                + ":"
                + aProperties.getProperty("productId");

        return commandId;
    }

    private MessageProducer messageProducer() {
        Exchange exchange =
            Exchange.fanOutInstance(
                    ConnectionSettings.instance(),
                    Exchanges.COLLABORATION_EXCHANGE_NAME,
                    true);

        MessageProducer messageProducer =
                MessageProducer.instance(exchange);

        return messageProducer;
    }

    private Properties parametersFrom(NotificationReader aReader) {
        Properties properties = new Properties();

        properties.put("command", COMMAND);

        properties.put("tenantId",
                aReader.eventStringValue("tenantId.id"));

        ProductDiscussionExclusiveOwnerId exclusiveOwnerId =
                new ProductDiscussionExclusiveOwnerId(
                        aReader.eventStringValue("productId.id"));

        properties.put("exclusiveOwnerId",
                exclusiveOwnerId.encoded());

        properties.put("forumSubject",
                "ProjectOvation Forum: " + aReader.eventStringValue("name"));

        properties.put("forumDescription",
                "About: " + aReader.eventStringValue("description"));

        properties.put("discussionSubject",
                "Product Discussion: " + aReader.eventStringValue("name"));

        String productOwnerId =
                aReader.eventStringValue("productOwnerId.id");

        properties.put("creatorId", productOwnerId);

        properties.put("moderatorId", productOwnerId);

        return properties;
    }
}
