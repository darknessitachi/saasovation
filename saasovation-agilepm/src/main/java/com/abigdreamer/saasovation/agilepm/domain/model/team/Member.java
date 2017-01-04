package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Date;

import com.abigdreamer.saasovation.agilepm.domain.model.Entity;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


public abstract class Member extends Entity {

    private MemberChangeTracker changeTracker;
    private String emailAddress;
    private boolean enabled = true;
    private String firstName;
    private String lastName;
    private TenantId tenantId;
    private String username;

    public Member(
            TenantId aTenantId,
            String aUsername,
            String aFirstName,
            String aLastName,
            String anEmailAddress,
            Date anInitializedOn) {

        this(aTenantId, aUsername, aFirstName, aLastName, anEmailAddress);

        this.setChangeTracker(
                new MemberChangeTracker(
                        anInitializedOn,
                        anInitializedOn,
                        anInitializedOn));
    }

    /**
     * 改变Email
     * @param anEmailAddress
     * @param asOfDate
     */
    public void changeEmailAddress(String anEmailAddress, Date asOfDate) {
        if (this.changeTracker().canChangeEmailAddress(asOfDate) &&
            !this.emailAddress().equals(anEmailAddress)) {
            this.setEmailAddress(anEmailAddress);
            this.setChangeTracker(this.changeTracker().emailAddressChangedOn(asOfDate));
        }
    }

    /**
     * 改变姓名
     * @param aFirstName
     * @param aLastName
     * @param asOfDate
     */
    public void changeName(String aFirstName, String aLastName, Date asOfDate) {
        if (this.changeTracker().canChangeName(asOfDate)) {
            this.setFirstName(aFirstName);
            this.setLastName(aLastName);
            this.setChangeTracker(this.changeTracker().nameChangedOn(asOfDate));
        }
    }

    public void disable(Date asOfDate) {
        if (this.changeTracker().canToggleEnabling(asOfDate)) {
            this.setEnabled(false);
            this.setChangeTracker(this.changeTracker().enablingOn(asOfDate));
        }
    }

    public void enable(Date asOfDate) {
        if (this.changeTracker().canToggleEnabling(asOfDate)) {
            this.setEnabled(true);
            this.setChangeTracker(this.changeTracker().enablingOn(asOfDate));
        }
    }

    public String emailAddress() {
        return this.emailAddress;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public String firstName() {
        return this.firstName;
    }

    public String lastName() {
        return this.lastName;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public String username() {
        return this.username;
    }

    protected Member(
            TenantId aTenantId,
            String aUsername,
            String aFirstName,
            String aLastName,
            String anEmailAddress) {

        this();

        this.setEmailAddress(anEmailAddress);
        this.setFirstName(aFirstName);
        this.setLastName(aLastName);
        this.setTenantId(aTenantId);
        this.setUsername(aUsername);
    }

    protected Member() {
        super();
    }

    private MemberChangeTracker changeTracker() {
        return this.changeTracker;
    }

    private void setChangeTracker(MemberChangeTracker aChangeTracker) {
        this.changeTracker = aChangeTracker;
    }

    private void setEmailAddress(String anEmailAddress) {
        if (anEmailAddress != null) {
            this.assertArgumentLength(anEmailAddress, 100, "Email address must be 100 characters or less.");
        }

        this.emailAddress = anEmailAddress;
    }

    private void setEnabled(boolean anEnabled) {
        this.enabled = anEnabled;
    }

    private void setFirstName(String aFirstName) {
        if (aFirstName != null) {
            this.assertArgumentLength(aFirstName, 50, "First name must be 50 characters or less.");
        }

        this.firstName = aFirstName;
    }

    private void setLastName(String aLastName) {
        if (aLastName != null) {
            this.assertArgumentLength(aLastName, 50, "Last name must be 50 characters or less.");
        }

        this.lastName = aLastName;
    }

    private void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenant id must be provided.");

        this.tenantId = aTenantId;
    }

    private void setUsername(String aUsername) {
        this.assertArgumentNotEmpty(aUsername, "The username must be provided.");
        this.assertArgumentLength(aUsername, 250, "The username must be 250 characters or less.");

        this.username = aUsername;
    }
}
