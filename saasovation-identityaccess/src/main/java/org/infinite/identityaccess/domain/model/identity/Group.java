package org.infinite.identityaccess.domain.model.identity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.abigdreamer.infinity.ddd.domain.model.ConcurrencySafeEntity;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import org.infinite.identityaccess.domain.event.identity.GroupGroupAdded;
import org.infinite.identityaccess.domain.event.identity.GroupGroupRemoved;
import org.infinite.identityaccess.domain.event.identity.GroupUserAdded;
import org.infinite.identityaccess.domain.event.identity.GroupUserRemoved;
import org.infinite.identityaccess.domain.service.GroupMemberService;



/**
 * 组
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:40:33
 * @version V1.0
 */
public class Group extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    public static final String ROLE_GROUP_PREFIX = "ROLE-INTERNAL-GROUP: ";

    private String name;
    private String description;
    
    private Set<GroupMember> groupMembers;
    
    private TenantId tenantId;

    public Group(TenantId aTenantId, String aName, String aDescription) {
        this();

        this.setDescription(aDescription);
        this.setName(aName);
        this.setTenantId(aTenantId);
    }

    /**
     * 添加组成员
     * @param aGroup 组
     * @param aGroupMemberService 组成员服务
     */
    public void addGroup(Group aGroup, GroupMemberService aGroupMemberService) {
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");
        this.assertArgumentFalse(aGroupMemberService.isMemberGroup(aGroup, this.toGroupMember()), "Group recurrsion.");

        if (this.groupMembers().add(aGroup.toGroupMember()) && !this.isInternalGroup()) {
            DomainEventPublisher
                .instance()
                .publish(new GroupGroupAdded(
                        this.tenantId(),
                        this.name(),
                        aGroup.name()));
        }
    }

    public void addUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this group.");
        this.assertArgumentTrue(aUser.isEnabled(), "User is not enabled.");

        if (this.groupMembers().add(aUser.toGroupMember()) && !this.isInternalGroup()) {
            DomainEventPublisher
                .instance()
                .publish(new GroupUserAdded(
                        this.tenantId(),
                        this.name(),
                        aUser.username()));
        }
    }

    public String description() {
        return this.description;
    }

    public Set<GroupMember> groupMembers() {
        return this.groupMembers;
    }

    // 判断人员是否是组中的成员
    public boolean isMember(User aUser, GroupMemberService aGroupMemberService) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this group.");
        this.assertArgumentTrue(aUser.isEnabled(), "User is not enabled.");

        boolean isMember =
            this.groupMembers().contains(aUser.toGroupMember());

        if (isMember) {
            isMember = aGroupMemberService.confirmUser(this, aUser);
        } else {
            isMember = aGroupMemberService.isUserInNestedGroup(this, aUser);
        }

        return isMember;
    }

    public String name() {
        return this.name;
    }

    public void removeGroup(Group aGroup) {
        this.assertArgumentNotNull(aGroup, "Group must not be null.");
        this.assertArgumentEquals(this.tenantId(), aGroup.tenantId(), "Wrong tenant for this group.");

        // not a nested remove, only direct member
        if (this.groupMembers().remove(aGroup.toGroupMember()) && !this.isInternalGroup()) {
            DomainEventPublisher
                .instance()
                .publish(new GroupGroupRemoved(
                        this.tenantId(),
                        this.name(),
                        aGroup.name()));
        }
    }

    public void removeUser(User aUser) {
        this.assertArgumentNotNull(aUser, "User must not be null.");
        this.assertArgumentEquals(this.tenantId(), aUser.tenantId(), "Wrong tenant for this group.");

        // not a nested remove, only direct member
        if (this.groupMembers().remove(aUser.toGroupMember()) && !this.isInternalGroup()) {
            DomainEventPublisher
                .instance()
                .publish(new GroupUserRemoved(
                        this.tenantId(),
                        this.name(),
                        aUser.username()));
        }
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            Group typedObject = (Group) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.name().equals(typedObject.name());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (2061 * 193)
            + this.tenantId().hashCode()
            + this.name().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "Group [description=" + description + ", name=" + name + ", tenantId=" + tenantId + "]";
    }

    protected Group() {
        super();

        this.setGroupMembers(new HashSet<GroupMember>(0));
    }

    protected void setDescription(String aDescription) {
        this.assertArgumentNotEmpty(aDescription, "Group description is required.");
        this.assertArgumentLength(aDescription, 1, 250, "Group description must be 250 characters or less.");

        this.description = aDescription;
    }

    protected void setGroupMembers(Set<GroupMember> aGroupMembers) {
        this.groupMembers = aGroupMembers;
    }

    protected boolean isInternalGroup() {
        return this.isInternalGroup(this.name());
    }

    protected boolean isInternalGroup(String aName) {
        return aName.startsWith(ROLE_GROUP_PREFIX);
    }

    protected void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "Group name is required.");
        this.assertArgumentLength(aName, 1, 100, "Group name must be 100 characters or less.");

        if (this.isInternalGroup(aName)) {
            String uuid = aName.substring(ROLE_GROUP_PREFIX.length());

            try {
                UUID.fromString(uuid);
            } catch (Exception e) {
                throw new IllegalArgumentException("The group name has an invalid format.");
            }
        }

        this.name = aName;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId must be provided.");

        this.tenantId = aTenantId;
    }

    // 将组转换为组成员
    protected GroupMember toGroupMember() {
        GroupMember groupMember =
            new GroupMember(
                    this.tenantId(),
                    this.name(),
                    GroupMemberType.Group);

        return groupMember;
    }
}
