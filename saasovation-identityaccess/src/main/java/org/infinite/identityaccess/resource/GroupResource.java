package org.infinite.identityaccess.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.infinite.identityaccess.domain.model.identity.Group;

import com.abigdreamer.infinity.common.serializer.ObjectSerializer;
import com.abigdreamer.infinity.ddd.media.OvationsMediaType;


/**
 * 组资源
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:28:39
 * @version V1.0
 */
@Path("/tenants/{tenantId}/groups")
public class GroupResource extends AbstractResource {

    public GroupResource() {
        super();
    }

    @GET
    @Path("{groupName}")
    @Produces({ OvationsMediaType.ID_OVATION_TYPE })
    public Response getGroup(
            @PathParam("tenantId") String aTenantId,
            @PathParam("groupName") String aGroupName,
            @Context Request aRequest) {

        Group group =
                this.identityApplicationService()
                    .group(aTenantId, aGroupName);

        if (group == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Response response = this.groupResponse(aRequest, group);

        return response;
    }

    private Response groupResponse(
            Request aRequest,
            Group aGroup) {

        Response response = null;

        String representation = ObjectSerializer.instance().serialize(aGroup);

        response =
            Response
                .ok(representation)
                .cacheControl(this.cacheControlFor(30))
                .build();

        return response;
    }
}
