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

package org.infinite.identityaccess.resource;

import com.abigdreamer.infinity.ddd.media.RepresentationReader;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.identity.Group;
import org.jboss.resteasy.client.ClientRequest;


public class GroupResourceTest extends ResourceTestCase {

    public GroupResourceTest() {
        super();
    }

    public void testGetGroup() throws Exception {
        Group group = this.group1Aggregate();
        DomainRegistry.groupRepository().add(group);

        String url = "http://127.0.0.1:" + PORT + "/tenants/{tenantId}/groups/{groupName}";

        System.out.println(">>> GET: " + url);
        ClientRequest request = new ClientRequest(url);
        request.pathParameter("tenantId", group.tenantId().id());
        request.pathParameter("groupName", group.name());
        String output = request.getTarget(String.class);
        System.out.println(output);

        RepresentationReader reader = new RepresentationReader(output);

        assertEquals(group.tenantId().id(), reader.stringValue("tenantId.id"));
        assertEquals(group.name(), reader.stringValue("name"));
    }
}
