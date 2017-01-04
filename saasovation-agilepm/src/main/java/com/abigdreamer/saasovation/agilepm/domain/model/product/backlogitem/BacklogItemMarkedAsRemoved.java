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

package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

import java.util.Date;

import com.abigdreamer.infinity.ddd.domain.model.DomainEvent;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


public class BacklogItemMarkedAsRemoved implements DomainEvent {

    private BacklogItemId backlogItemId;
    private int eventVersion;
    private Date occurredOn;
    private TenantId tenantId;

    public BacklogItemMarkedAsRemoved(TenantId aTenantId, BacklogItemId aBacklogItemId) {
        super();

        this.backlogItemId = aBacklogItemId;
        this.eventVersion = 1;
        this.occurredOn = new Date();
        this.tenantId = aTenantId;
    }

    public BacklogItemId backlogItemId() {
        return this.backlogItemId;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }
}
