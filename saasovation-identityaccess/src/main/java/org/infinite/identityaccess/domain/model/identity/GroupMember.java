package org.infinite.identityaccess.domain.model.identity;


import com.abigdreamer.infinity.ddd.domain.model.IdentifiedValueObject;


/**
 * 成员
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:41:38
 * @version V1.0
 */
public class GroupMember extends IdentifiedValueObject {

    private static final long serialVersionUID = 1L;

    private String name;
    private TenantId tenantId;
    private GroupMemberType type;

    public boolean isGroup() {
        return this.type().isGroup();
    }

    public boolean isUser() {
        return this.type().isUser();
    }

    public String name() {
        return this.name;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public GroupMemberType type() {
        return this.type;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            GroupMember typedObject = (GroupMember) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.name().equals(typedObject.name()) &&
                this.type().equals(typedObject.type());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (21941 * 197)
            + this.tenantId().hashCode()
            + this.name().hashCode()
            + this.type().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "GroupMember [name=" + name + ", tenantId=" + tenantId + ", type=" + type + "]";
    }

    protected GroupMember(TenantId aTenantId, String aName, GroupMemberType aType) {
        this();

        this.setName(aName);
        this.setTenantId(aTenantId);
        this.setType(aType);
    }

    protected GroupMember() {
        super();
    }

    protected void setName(String aName) {
        this.assertArgumentNotEmpty(aName, "Member name is required.");
        this.assertArgumentLength(aName, 1, 100, "Member name must be 100 characters or less.");

        this.name = aName;
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId must be provided.");

        this.tenantId = aTenantId;
    }

    protected void setType(GroupMemberType aType) {
        this.assertArgumentNotNull(aType, "The type must be provided.");

        this.type = aType;
    }
}
