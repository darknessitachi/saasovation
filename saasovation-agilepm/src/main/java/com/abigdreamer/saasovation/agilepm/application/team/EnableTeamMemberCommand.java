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

package com.abigdreamer.saasovation.agilepm.application.team;

import java.util.Date;

public class EnableTeamMemberCommand extends EnableMemberCommand {

    public EnableTeamMemberCommand(
            String tenantId,
            String username,
            String firstName,
            String lastName,
            String emailAddress,
            Date occurredOn) {

        super(tenantId, username, firstName, lastName, emailAddress, occurredOn);
    }

    public EnableTeamMemberCommand() {
        super();
    }
}
