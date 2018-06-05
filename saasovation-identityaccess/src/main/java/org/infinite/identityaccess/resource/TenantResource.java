package org.infinite.identityaccess.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.infinite.identityaccess.application.ApplicationServiceRegistry;
import org.infinite.identityaccess.application.IdentityApplicationService;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.jboss.resteasy.annotations.cache.Cache;

import com.abigdreamer.infinity.ddd.media.OvationsMediaType;
import com.rapidark.framework.commons.serializer.ObjectSerializer;


/**
 * 租赁资源
 * 
 * @author Darkness
 * @date 2014-5-28 下午10:30:03
 * @version V1.0
 */
@Path("/tenants")
public class TenantResource {

    public TenantResource() {
        super();
    }

    @GET
    @Path("{tenantId}")
    @Produces({ OvationsMediaType.ID_OVATION_TYPE })
    @Cache(maxAge=3600)
    public Response getTenant(@PathParam("tenantId") String aTenantId) {

        Tenant tenant = this.identityApplicationService().tenant(aTenantId);

        if (tenant == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        String tenantRepresentation = ObjectSerializer.instance().serialize(tenant);

        Response response = Response.ok(tenantRepresentation).build();

        return response;
    }

    private IdentityApplicationService identityApplicationService() {
        return ApplicationServiceRegistry.identityApplicationService();
    }
}
