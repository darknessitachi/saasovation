package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Date;

import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 *  团队成员
 * 
 * @author Darkness
 * @date 2014-5-10 下午4:30:19 
 * @version V1.0
 */
public class TeamMember extends Member {

    public TeamMember(
            TenantId aTenantId,
            String aUsername,
            String aFirstName,
            String aLastName,
            String anEmailAddress,
            Date anInitializedOn) {

        super(aTenantId, aUsername, aFirstName, aLastName, anEmailAddress, anInitializedOn);
    }

    public TeamMemberId teamMemberId() {
        return new TeamMemberId(this.tenantId(), this.username());
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            TeamMember typedObject = (TeamMember) anObject;
            equalObjects =
                    this.tenantId().equals(typedObject.tenantId()) &&
                    this.username().equals(typedObject.username());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
                + (36305 * 89)
                + this.tenantId().hashCode()
                + this.username().hashCode();

        return hashCodeValue;
    }

    protected TeamMember() {
        super();
    }
}
