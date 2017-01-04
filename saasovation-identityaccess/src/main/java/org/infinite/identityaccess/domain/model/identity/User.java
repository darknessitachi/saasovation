package org.infinite.identityaccess.domain.model.identity;


import com.abigdreamer.infinity.ddd.domain.model.ConcurrencySafeEntity;
import com.abigdreamer.infinity.ddd.domain.model.DomainEventPublisher;
import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.event.identity.UserEnablementChanged;
import org.infinite.identityaccess.domain.event.identity.UserPasswordChanged;
import org.infinite.identityaccess.domain.event.identity.UserRegistered;


/**
 *  用户
 * 
 * @author Darkness
 * @date 2014-5-10 下午8:45:53 
 * @version V1.0
 */
public class User extends ConcurrencySafeEntity {

    private static final long serialVersionUID = 1L;

    private Enablement enablement;
    
    private TenantId tenantId;
    private String username;
    private String password;
    
    private Person person;
    
    public void changePassword(String aCurrentPassword, String aChangedPassword) {
        this.assertArgumentNotEmpty(
                aCurrentPassword,
                "Current and new password must be provided.");

        this.assertArgumentEquals(
                this.password(),
                this.asEncryptedValue(aCurrentPassword),
                "Current password not confirmed.");

        this.protectPassword(aCurrentPassword, aChangedPassword);

        DomainEventPublisher
            .instance()
            .publish(new UserPasswordChanged(
                    this.tenantId(),
                    this.username()));
    }

    public void changePersonalContactInformation(ContactInformation aContactInformation) {
        this.person().changeContactInformation(aContactInformation);
    }

    public void changePersonalName(FullName aPersonalName) {
        this.person().changeName(aPersonalName);
    }

    // 设定用户可用状态
    public void defineEnablement(Enablement anEnablement) {
        this.setEnablement(anEnablement);

        DomainEventPublisher
            .instance()
            .publish(new UserEnablementChanged(
                    this.tenantId(),
                    this.username(),
                    this.enablement()));
    }

    public boolean isEnabled() {
        return this.enablement().isEnablementEnabled();
    }

    public Person person() {
        return this.person;
    }

    public TenantId tenantId() {
        return this.tenantId;
    }

    public UserDescriptor userDescriptor() {
        return new UserDescriptor(
                this.tenantId(),
                this.username(),
                this.person().emailAddress().address());
    }

    public String username() {
        return this.username;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            User typedObject = (User) anObject;
            equalObjects =
                this.tenantId().equals(typedObject.tenantId()) &&
                this.username().equals(typedObject.username());
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (45217 * 269)
            + this.tenantId().hashCode()
            + this.username().hashCode();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "User [tenantId=" + tenantId + ", username=" + username
                + ", person=" + person + ", enablement=" + enablement + "]";
    }

    protected User(
            TenantId aTenantId,
            String aUsername,
            String aPassword,
            Enablement anEnablement,
            Person aPerson) {

        this();

        this.setEnablement(anEnablement);
        this.setPerson(aPerson);
        this.setTenantId(aTenantId);
        this.setUsername(aUsername);

        this.protectPassword("", aPassword);

        aPerson.internalOnlySetUser(this);

        DomainEventPublisher
            .instance()
            .publish(new UserRegistered(
                    this.tenantId(),
                    aUsername,
                    aPerson.name(),
                    aPerson.contactInformation().emailAddress()));
    }

    protected User() {
        super();
    }

    protected String asEncryptedValue(String aPlainTextPassword) {
        String encryptedValue =
            DomainRegistry
                .encryptionService()
                .encryptedValue(aPlainTextPassword);

        return encryptedValue;
    }

    protected void assertPasswordsNotSame(String aCurrentPassword, String aChangedPassword) {
        this.assertArgumentNotEquals(
                aCurrentPassword,
                aChangedPassword,
                "The password is unchanged.");
    }

    protected void assertPasswordNotWeak(String aPlainTextPassword) {
        this.assertArgumentFalse(
                DomainRegistry.passwordService().isWeak(aPlainTextPassword),
                "The password must be stronger.");
    }

    protected void assertUsernamePasswordNotSame(String aPlainTextPassword) {
        this.assertArgumentNotEquals(
                this.username(),
                aPlainTextPassword,
                "The username and password must not be the same.");
    }

    protected Enablement enablement() {
        return this.enablement;
    }

    protected void setEnablement(Enablement anEnablement) {
        this.assertArgumentNotNull(anEnablement, "The enablement is required.");

        this.enablement = anEnablement;
    }

    public String internalAccessOnlyEncryptedPassword() {
        return this.password();
    }

    protected String password() {
        return this.password;
    }

    protected void setPassword(String aPassword) {
        this.password = aPassword;
    }

    protected void setPerson(Person aPerson) {
        this.assertArgumentNotNull(aPerson, "The person is required.");

        this.person = aPerson;
    }

    protected void protectPassword(String aCurrentPassword, String aChangedPassword) {
        this.assertPasswordsNotSame(aCurrentPassword, aChangedPassword);

        this.assertPasswordNotWeak(aChangedPassword);

        this.assertUsernamePasswordNotSame(aChangedPassword);

        this.setPassword(this.asEncryptedValue(aChangedPassword));
    }

    protected void setTenantId(TenantId aTenantId) {
        this.assertArgumentNotNull(aTenantId, "The tenantId is required.");

        this.tenantId = aTenantId;
    }

    protected GroupMember toGroupMember() {
        GroupMember groupMember =
            new GroupMember(
                    this.tenantId(),
                    this.username(),
                    GroupMemberType.User);

        return groupMember;
    }

    protected void setUsername(String aUsername) {
        this.assertArgumentNotEmpty(aUsername, "The username is required.");
        this.assertArgumentLength(aUsername, 3, 250, "The username must be 3 to 250 characters.");

        this.username = aUsername;
    }
}
