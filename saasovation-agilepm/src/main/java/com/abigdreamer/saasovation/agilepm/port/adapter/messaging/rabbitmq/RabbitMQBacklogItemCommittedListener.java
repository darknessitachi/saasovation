package com.abigdreamer.saasovation.agilepm.port.adapter.messaging.rabbitmq;

import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.Exchanges;
import com.abigdreamer.saasovation.agilepm.application.sprint.CommitBacklogItemToSprintCommand;
import com.abigdreamer.saasovation.agilepm.application.sprint.SprintApplicationService;


public class RabbitMQBacklogItemCommittedListener implements ExchangeListener {

    private SprintApplicationService sprintApplicationService;

    public RabbitMQBacklogItemCommittedListener(
            SprintApplicationService aSprintApplicationService) {

        super();

        this.sprintApplicationService = aSprintApplicationService;
    }

    @Override
    public String exchangeName() {
        return Exchanges.AGILEPM_EXCHANGE_NAME;
    }

    @Override
    public void filteredDispatch(String aType, String aTextMessage) {
        NotificationReader reader = new NotificationReader(aTextMessage);

        String tenantId = reader.eventStringValue("tenant.id");
        String backlogItemId = reader.eventStringValue("backlogItemId.id");
        String committedToSprintId = reader.eventStringValue("committedToSprintId.id");

        this.sprintApplicationService().commitBacklogItemToSprint(
                new CommitBacklogItemToSprintCommand(
                    tenantId,
                    committedToSprintId,
                    backlogItemId));
    }

    @Override
    public String[] listensTo() {
        return new String[] {
                "com.saasovation.agilepm.domain.model.product.backlogitem.BacklogItemCommitted"
        };
    }

    private SprintApplicationService sprintApplicationService() {
        return this.sprintApplicationService;
    }
    
    @Override
    public String name() {
    	return this.getClass().getName();
    }
}
