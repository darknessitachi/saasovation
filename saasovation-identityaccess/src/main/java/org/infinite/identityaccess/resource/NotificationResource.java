package org.infinite.identityaccess.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.abigdreamer.infinity.common.serializer.ObjectSerializer;
import com.abigdreamer.infinity.ddd.media.Link;
import com.abigdreamer.infinity.ddd.media.OvationsMediaType;
import com.abigdreamer.infinity.ddd.notification.NotificationLog;
import com.abigdreamer.infinity.ddd.notification.application.representation.NotificationLogRepresentation;


/**
 *  REST 方式发布当前日志和存档日志
 * 
 * @author Darkness
 * @date 2014-5-5 下午8:38:09 
 * @version V1.0
 */
@Path("/notifications")
public class NotificationResource extends AbstractResource {

    public NotificationResource() {
        super();
    }

    @GET
    @Produces({ OvationsMediaType.ID_OVATION_TYPE })
    public Response getCurrentNotificationLog(@Context UriInfo aUriInfo) {

        NotificationLog currentNotificationLog =
            this.notificationApplicationService()
                .currentNotificationLog();

        if (currentNotificationLog == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Response response = this.currentNotificationLogResponse(currentNotificationLog, aUriInfo);

        return response;
    }

    @GET
    @Path("{notificationId}")
    @Produces({ OvationsMediaType.ID_OVATION_TYPE })
    public Response getNotificationLog(
            @PathParam("notificationId") String aNotificationId,
            @Context UriInfo aUriInfo) {

        NotificationLog notificationLog =
            this.notificationApplicationService()
                .notificationLog(aNotificationId);

        if (notificationLog == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        Response response =
            this.notificationLogResponse(
                    notificationLog,
                    aUriInfo);

        return response;
    }

    private Response currentNotificationLogResponse(
            NotificationLog aCurrentNotificationLog,
            UriInfo aUriInfo) {

        NotificationLogRepresentation log =
            new NotificationLogRepresentation(aCurrentNotificationLog);

        log.setLinkSelf(
                this.selfLink(aCurrentNotificationLog, aUriInfo));

        log.setLinkPrevious(
            this.previousLink(aCurrentNotificationLog, aUriInfo));

        String serializedLog = ObjectSerializer.instance().serialize(log);

        Response response =
            Response
                .ok(serializedLog)
                .cacheControl(this.cacheControlFor(60))
                .build();

        return response;
    }

    private Response notificationLogResponse(
            NotificationLog aNotificationLog,
            UriInfo aUriInfo) {

        NotificationLogRepresentation log =
            new NotificationLogRepresentation(aNotificationLog);

        log.setLinkSelf(this.selfLink(aNotificationLog, aUriInfo));

        log.setLinkNext(this.nextLink(aNotificationLog, aUriInfo));

        log.setLinkPrevious(this.previousLink(aNotificationLog, aUriInfo));

        String serializedLog = ObjectSerializer.instance().serialize(log);

        Response response =
            Response
                .ok(serializedLog)
                .cacheControl(this.cacheControlFor(3600))
                .build();

        return response;
    }

    private Link linkFor(
            String aRelationship,
            String anId,
            UriInfo aUriInfo) {

        Link link = null;

        if (anId != null) {

            UriBuilder builder = aUriInfo.getBaseUriBuilder();

            String linkUrl =
                builder
                    .path("notifications")
                    .path(anId)
                    .build()
                    .toString();

            link = new Link(
                    linkUrl,
                    aRelationship,
                    null,
                    OvationsMediaType.ID_OVATION_TYPE);
        }

        return link;
    }

    private Link nextLink(
            NotificationLog aNotificationLog,
            UriInfo aUriInfo) {
        return
            this.linkFor(
                    "next",
                    aNotificationLog.nextNotificationLogId(),
                    aUriInfo);
    }

    private Link previousLink(
            NotificationLog aNotificationLog,
            UriInfo aUriInfo) {

        return
            this.linkFor(
                    "previous",
                    aNotificationLog.previousNotificationLogId(),
                    aUriInfo);
    }

    private Link selfLink(
            NotificationLog aNotificationLog,
            UriInfo aUriInfo) {
        return
            this.linkFor(
                    "self",
                    aNotificationLog.notificationLogId(),
                    aUriInfo);
    }
}
