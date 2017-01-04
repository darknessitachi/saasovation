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

package com.abigdreamer.saasovation.agilepm.port.adapter.messaging.sloth;

import java.util.Date;

import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.ExchangeListener;
import com.abigdreamer.infinity.ddd.port.adapter.messaging.Exchanges;
import com.abigdreamer.saasovation.agilepm.application.team.ChangeTeamMemberNameCommand;
import com.abigdreamer.saasovation.agilepm.application.team.TeamApplicationService;


public class SlothMQTeamMemberNameChangedListener implements ExchangeListener {

    private TeamApplicationService teamApplicationService;

    public SlothMQTeamMemberNameChangedListener() {
        super();
    }

    public String exchangeName() {
        return Exchanges.IDENTITY_ACCESS_EXCHANGE_NAME;
    }

    public void filteredDispatch(String aType, String aTextMessage) {
        NotificationReader reader = new NotificationReader(aTextMessage);

        String firstName = reader.eventStringValue("name.firstName");
        String lastName = reader.eventStringValue("name.lastName");
        String tenantId = reader.eventStringValue("tenantId.id");
        String username = reader.eventStringValue("username");
        Date occurredOn = reader.occurredOn();

        this.teamApplicationService().changeTeamMemberName(
                new ChangeTeamMemberNameCommand(
                    tenantId,
                    username,
                    firstName,
                    lastName,
                    occurredOn));
    }

    public String[] listensTo() {
        return new String[] {
                "com.saasovation.identityaccess.domain.model.identity.PersonNameChanged"
                };
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    private TeamApplicationService teamApplicationService() {
        return this.teamApplicationService;
    }
}
