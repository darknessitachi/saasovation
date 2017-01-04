package org.infinite.identityaccess.domain.model.identity;

import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.ContactInformation;
import org.infinite.identityaccess.domain.model.identity.EmailAddress;
import org.infinite.identityaccess.domain.model.identity.PostalAddress;
import org.infinite.identityaccess.domain.model.identity.Telephone;


/**
 * 联系信息测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午4:58:05
 * @version V1.0
 */
public class ContactInformationTest extends IdentityAccessTest {

    public ContactInformationTest() {
        super();
    }

    // 测试联系信息
    public void testContactInformation() throws Exception {
        ContactInformation contactInformation = this.contactInformation();

        assertEquals(FIXTURE_USER_EMAIL_ADDRESS, contactInformation.emailAddress().address());
        assertEquals("Boulder", contactInformation.postalAddress().city());
        assertEquals("CO", contactInformation.postalAddress().stateProvince());
    }

    // 测试改变Email
    public void testChangeEmailAddress() throws Exception {
        ContactInformation contactInformation = this.contactInformation();
        ContactInformation contactInformationCopy = new ContactInformation(contactInformation);

        ContactInformation contactInformation2 =
                contactInformation
                    .changeEmailAddress(
                            new EmailAddress(FIXTURE_USER_EMAIL_ADDRESS2));

        assertEquals(contactInformationCopy, contactInformation);
        assertFalse(contactInformation.equals(contactInformation2));
        assertFalse(contactInformationCopy.equals(contactInformation2));

        assertEquals(FIXTURE_USER_EMAIL_ADDRESS, contactInformation.emailAddress().address());
        assertEquals(FIXTURE_USER_EMAIL_ADDRESS2, contactInformation2.emailAddress().address());
        assertEquals("Boulder", contactInformation.postalAddress().city());
        assertEquals("CO", contactInformation.postalAddress().stateProvince());
    }

    // 测试改变邮政地址
    public void testChangePostalAddress() throws Exception {
        ContactInformation contactInformation = this.contactInformation();
        ContactInformation contactInformationCopy = new ContactInformation(contactInformation);

        ContactInformation contactInformation2 =
                contactInformation
                    .changePostalAddress(
                            new PostalAddress("321 Mockingbird Lane", "Denver", "CO", "81121", "US"));

        assertEquals(contactInformationCopy, contactInformation);
        assertFalse(contactInformation.equals(contactInformation2));
        assertFalse(contactInformationCopy.equals(contactInformation2));

        assertEquals("321 Mockingbird Lane", contactInformation2.postalAddress().streetAddress());
        assertEquals("Denver", contactInformation2.postalAddress().city());
        assertEquals("CO", contactInformation2.postalAddress().stateProvince());
    }

    // 测试改变主号码
    public void testChangePrimaryTelephone() throws Exception {
        ContactInformation contactInformation = this.contactInformation();
        ContactInformation contactInformationCopy = new ContactInformation(contactInformation);

        ContactInformation contactInformation2 =
                contactInformation
                    .changePrimaryTelephone(
                            new Telephone("720-555-1212"));

        assertEquals(contactInformationCopy, contactInformation);
        assertFalse(contactInformation.equals(contactInformation2));
        assertFalse(contactInformationCopy.equals(contactInformation2));

        assertEquals("720-555-1212", contactInformation2.primaryTelephone().number());
        assertEquals("Boulder", contactInformation2.postalAddress().city());
        assertEquals("CO", contactInformation2.postalAddress().stateProvince());
    }

    // 测试改变副号码
    public void testChangeSecondaryTelephone() throws Exception {
        ContactInformation contactInformation = this.contactInformation();
        ContactInformation contactInformationCopy = new ContactInformation(contactInformation);

        ContactInformation contactInformation2 =
                contactInformation
                    .changeSecondaryTelephone(
                            new Telephone("720-555-1212"));

        assertEquals(contactInformationCopy, contactInformation);
        assertFalse(contactInformation.equals(contactInformation2));
        assertFalse(contactInformationCopy.equals(contactInformation2));

        assertEquals("720-555-1212", contactInformation2.secondaryTelephone().number());
        assertEquals("Boulder", contactInformation2.postalAddress().city());
        assertEquals("CO", contactInformation2.postalAddress().stateProvince());
    }
}
