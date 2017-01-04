package org.infinite.identityaccess.resource;

import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import com.abigdreamer.infinity.ddd.media.Link;
import com.abigdreamer.infinity.ddd.notification.NotificationLog;
import com.abigdreamer.infinity.ddd.notification.NotificationLogReader;
import com.abigdreamer.infinity.ddd.notification.NotificationReader;
import org.infinite.identityaccess.application.ApplicationServiceRegistry;
import org.infinite.identityaccess.application.command.ChangeEmailAddressCommand;
import org.infinite.identityaccess.application.command.ProvisionTenantCommand;
import org.infinite.identityaccess.application.command.RegisterUserCommand;
import org.infinite.identityaccess.domain.event.identity.PersonNameChanged;
import org.infinite.identityaccess.domain.event.identity.UserPasswordChanged;
import org.infinite.identityaccess.domain.model.identity.Person;
import org.infinite.identityaccess.domain.model.identity.Tenant;
import org.infinite.identityaccess.domain.model.identity.User;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;


public class NotificationResourceTest extends ResourceTestCase {

    public NotificationResourceTest() {
        super();
    }

    public void testBasicNotificationLog() throws Exception {
        this.generateUserEvents();

        NotificationLog currentNotificationLog =
            ApplicationServiceRegistry
                .notificationApplicationService()
                .currentNotificationLog();

        assertNotNull(currentNotificationLog);

        String url = "http://localhost:" + PORT + "/notifications";

        ClientRequest request = new ClientRequest(url);
        ClientResponse<String> response = request.get(String.class);
        String serializedNotifications = response.getEntity();
        System.out.println(serializedNotifications);

        NotificationLogReader log =
                new NotificationLogReader(serializedNotifications);

        assertFalse(log.isArchived());
        assertNotNull(log.id());

        for (NotificationReader notification : log) {

            String typeName = notification.typeName();

            assertTrue(typeName.endsWith("UserRegistered") ||
                       typeName.endsWith("PersonNameChanged") ||
                       typeName.endsWith("UserPasswordChanged"));
        }
    }

    public void testPersonContactInformationChangedNotification() throws Exception {
        this.generateUserEvents();

        ApplicationServiceRegistry
            .identityApplicationService()
            .changeUserEmailAddress(
                    new ChangeEmailAddressCommand(
                            this.tenantAggregate().tenantId().id(),
                            FIXTURE_USERNAME + 0,
                            FIXTURE_USER_EMAIL_ADDRESS2));

        String url = "http://localhost:" + PORT + "/notifications";

        ClientRequest request = new ClientRequest(url);
        ClientResponse<String> response = request.get(String.class);
        String serializedNotifications = response.getEntity();
        System.out.println(serializedNotifications);

        NotificationLogReader log =
                new NotificationLogReader(serializedNotifications);

        assertFalse(log.isArchived());
        assertNotNull(log.id());

        boolean found = false;

        for (NotificationReader notification : log) {
            String typeName = notification.typeName();

            if (typeName.endsWith("PersonContactInformationChanged")) {
                String emailAddress =
                        notification.eventStringValue(
                                "contactInformation.emailAddress.address");

                assertEquals(
                        FIXTURE_USER_EMAIL_ADDRESS2,
                        emailAddress);

                found = true;
            }
        }

        assertTrue(found);
    }

    public void testTenantProvisionedNotification() throws Exception {
        Tenant newTenant =
                ApplicationServiceRegistry
                    .identityApplicationService()
                    .provisionTenant(
                            new ProvisionTenantCommand(
                                    "All-Star Tenant",
                                    "An all-star company.",
                                    "Frank", "Oz",
                                    "frank@allstartcompany.com",
                                    "212-555-1211",
                                    "212-555-1212",
                                    "123 5th Avenue",
                                    "New York",
                                    "NY",
                                    "11201",
                                    "US"));

        assertNotNull(newTenant);

        String url = "http://localhost:" + PORT + "/notifications";

        ClientRequest request = new ClientRequest(url);
        ClientResponse<String> response = request.get(String.class);
        String serializedNotifications = response.getEntity();
        System.out.println(serializedNotifications);

        NotificationLogReader log =
                new NotificationLogReader(serializedNotifications);

        assertFalse(log.isArchived());
        assertNotNull(log.id());

        boolean found = false;

        for (NotificationReader notification : log) {
            String typeName = notification.typeName();

            if (typeName.endsWith("TenantProvisioned")) {
                String tenantId = notification.eventStringValue("tenantId.id");

                assertEquals(newTenant.tenantId().id(), tenantId);

                found = true;
            }
        }

        assertTrue(found);
    }

    public void testNotificationNavigation() throws Exception {
        this.generateUserEvents();

        String url = "http://localhost:" + PORT + "/notifications";

        ClientRequest request = new ClientRequest(url);
        ClientResponse<String> response = request.get(String.class);
        String serializedNotifications = response.getEntity();
        System.out.println(serializedNotifications);

        NotificationLogReader log = new NotificationLogReader(serializedNotifications);

        assertFalse(log.isArchived());
        assertNotNull(log.id());
        assertFalse(log.hasNext());
        assertTrue(log.hasSelf());
        assertTrue(log.hasPrevious());

        int count = 0;

        while (log.hasPrevious()) {
            ++count;

            Link previous = log.previous();

            request = new ClientRequest(previous.getHref());
            response = request.get(String.class);
            serializedNotifications = response.getEntity();

            //System.out.println(serializedNotifications);

            log = new NotificationLogReader(serializedNotifications);

            assertTrue(log.isArchived());
            assertNotNull(log.id());
            assertTrue(log.hasNext());
            assertTrue(log.hasSelf());
        }

        assertTrue(count >= 1);
    }

    private void generateUserEvents() {
        Tenant tenant = this.tenantAggregate();
        Person person = this.userAggregate().person();

        String invitationId =
                tenant.allAvailableRegistrationInvitations()
                       .iterator()
                       .next()
                       .invitationId();

        for (int idx = 0; idx < 25; ++idx) {

            User user =
                ApplicationServiceRegistry
                    .identityApplicationService()
                    .registerUser(
                        new RegisterUserCommand(
                                tenant.tenantId().id(),
                                invitationId,
                                FIXTURE_USERNAME + idx,
                                FIXTURE_PASSWORD,
                                "Zoe",
                                "Doe",
                                true,
                                null,
                                null,
                                person.contactInformation().emailAddress().address(),
                                person.contactInformation().primaryTelephone().number(),
                                person.contactInformation().secondaryTelephone().number(),
                                person.contactInformation().postalAddress().streetAddress(),
                                person.contactInformation().postalAddress().city(),
                                person.contactInformation().postalAddress().stateProvince(),
                                person.contactInformation().postalAddress().postalCode(),
                                person.contactInformation().postalAddress().countryCode()));

            if ((idx % 2) == 0) {
                PersonNameChanged event =
                        new PersonNameChanged(
                                user.tenantId(),
                                user.username(),
                                user.person().name());

                this.eventStore.append(event);
            }

            if ((idx % 3) == 0) {
                UserPasswordChanged event =
                        new UserPasswordChanged(
                                user.tenantId(),
                                user.username());

                this.eventStore.append(event);
            }

            DomainEventPublisher.instance().reset();
        }
    }
}
