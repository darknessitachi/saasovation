package org.infinite.identityaccess.domain.model.access;

import java.util.UUID;

import com.abigdreamer.infinity.ddd.domain.model.ConcurrencySafeEntity;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import org.infinite.identityaccess.domain.event.access.GroupAssignedToRole;
import org.infinite.identityaccess.domain.event.access.GroupUnassignedFromRole;
import org.infinite.identityaccess.domain.event.access.UserAssignedToRole;
import org.infinite.identityaccess.domain.event.access.UserUnassignedFromRole;
import org.infinite.identityaccess.domain.model.identity.Group;
import org.infinite.identityaccess.domain.model.identity.TenantId;
import org.infinite.identityaccess.domain.model.identity.User;
import org.infinite.identityaccess.domain.service.GroupMemberService;


/**
 * 角色
 * 
 * @author Darkness
 * @date 2014-5-28 下午9:26:37
 * @version V1.0
 */
public class Role extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    private String description;
    private Group group;
    private String name;
    private boolean supportsNesting = true;
    private TenantId tenantId;

    public Role(TenantId aTenantId, String aName, String aDescription) {
        this(aTenantId, aName, aDescription, false);
    }

    public Role(
            TenantId aTenantId,
            String aName,
            String aDescription,
            boolean aSupportsNesting) {

        this();

        this.setDescription(aDescription);
        this.setName(aName);
        this.setSupportsNesting(aSupportsNesting);
        this.setTenantId(aTenantId);

        this.createInternalGroup();
    }

    public void assignGroup(Group aGroup, GroupMemberService aGroupMemberService) {
        this.assertStateTrue(this.supportsNesting(), "This role does not support group nesting.");
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");

        this.group().addGroup(aGroup, aGroupMemberService);

        DomainEventPublisher
            .instance()
            .publish(new GroupAssignedToRole(
                    this.tenantId(),
                    this.name(),
                    aGroup.name()));
    }

    public void assignUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this user.");

        this.group().addUser(aUser);

        // NOTE: Consider what a consuming Bounded Context would
        // need to do if this event was not enriched with the
        // last three user person properties. (Hint: A lot.)
        DomainEventPublisher
            .instance()
            .publish(new UserAssignedToRole(
                    this.tenantId(),
                    this.name(),
                    aUser.username(),
                    aUser.person().name().firstName(),
                    aUser.person().name().lastName(),
                    aUser.person().emailAddress().address()));
    }

    public String description() {
        return this.description;
    }

    public boolean isInRole(User aUser, GroupMemberService aGroupMemberService) {
        return this.group().isMember(aUser, aGroupMemberService);
    }

    public String name() {
        return this.name;
    }

    public boolean supportsNesting() {
        return this.supportsNesting;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public void unassignGroup(Group aGroup) {
        this.assertStateTrue(this.supportsNesting(), "This role does not support group nesting.");
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");

        this.group().removeGroup(aGroup);

        DomainEventPublisher
            .instance()
            .publish(new GroupUnassignedFromRole(
                    this.tenantId(),
                    this.name(),
                    aGroup.name()));
    }

    public void unassignUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this user.");

        this.group().removeUser(aUser);

        DomainEventPublisher
            .instance()
            .publish(new UserUnassignedFromRole(
                    this.tenantId(),
                    this.name(),
                    aUser.username()));
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Role typedObject = (Role) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.name().equals(typedObject.name());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (18723 * 233)
            + this.tenantId().hashCode()
            + this.name().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Role [tenantId=" + tenantId + ", name=" + name
                + ", description=" + description + ", supportsNesting="
                + supportsNesting + ", group=" + group + "]";
    }

    protected Role() {
        super();
    }

    protected void createInternalGroup() {
        String groupName =
                Group.ROLE_GROUP_PREFIX
                + UUID.randomUUID().toString().toUpperCase();

        this.setGroup(new Group(
                this.tenantId(),
                groupName,
                "Role backing group for: " + this.name()));
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "Role description is required.");
        this.assertArgumentLength(aDescription, 1, 250, "Role description must be 250 characters or less.");

        this.description = aDescription;
    }

    protected Group group() {
        return this.group;
    }

    protected void setGroup(Group aGroup) {
        this.group = aGroup;
    }

    protected void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "Role name must be provided.");
        this.assertArgumentLength(aName, 1, 250, "Role name must be 100 characters or less.");

        this.name = aName;
    }

    protected void setSupportsNesting(boolean aSupportsNesting) {
        this.supportsNesting = aSupportsNesting;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId is required.");

        this.tenantId = aTenantId;
    }
}
