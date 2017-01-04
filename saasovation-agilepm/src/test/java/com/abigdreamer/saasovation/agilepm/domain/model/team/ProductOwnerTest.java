package com.abigdreamer.saasovation.agilepm.domain.model.team;

import java.util.Date;

import com.abigdreamer.saasovation.agilepm.domain.model.DomainRegistry;
import com.abigdreamer.saasovation.agilepm.domain.model.team.ProductOwner;
import com.abigdreamer.saasovation.agilepm.domain.model.tenant.TenantId;


/**
 * 产品负责人测试
 * 
 * @author Darkness
 * @date 2014-5-29 下午9:43:56
 * @version V1.0
 */
public class ProductOwnerTest extends TeamCommonTest {

    public ProductOwnerTest() {
        super();
    }

    // 测试创建项目负责人
    public void testCreate() throws Exception {
        ProductOwner productOwner =
                new ProductOwner(
                        new TenantId("T-12345"),
                        "zoe",
                        "Zoe",
                        "Doe",
                        "zoe@saasovation.com",
                        new Date());

        assertNotNull(productOwner);

        DomainRegistry.productOwnerRepository().save(productOwner);

        assertEquals("zoe", productOwner.username());
        assertEquals("Zoe", productOwner.firstName());
        assertEquals("Doe", productOwner.lastName());
        assertEquals("zoe@saasovation.com", productOwner.emailAddress());
        assertEquals(productOwner.username(), productOwner.productOwnerId().id());
    }

    // 测试改变项目负责人Email
    public void testChangeEmailAddress() throws Exception {
        ProductOwner productOwner = this.productOwnerForTest();

        assertFalse(productOwner.emailAddress().equals("zoedoe@saasovation.com"));

        // later...
        Date notificationOccurredOn = new Date();

        productOwner.changeEmailAddress("zoedoe@saasovation.com", notificationOccurredOn);

        assertEquals("zoedoe@saasovation.com", productOwner.emailAddress());
    }

    // 测试改变姓名
    public void testChangeName() throws Exception {
        ProductOwner productOwner = this.productOwnerForTest();

        assertFalse(productOwner.lastName().equals("Doe-Jones"));

        // later...
        Date notificationOccurredOn = new Date();

        productOwner.changeName("Zoe", "Doe-Jones", notificationOccurredOn);

        assertEquals("Zoe", productOwner.firstName());
        assertEquals("Doe-Jones", productOwner.lastName());
    }

    // 测试禁用
    public void testDisable() throws Exception {
        ProductOwner productOwner = this.productOwnerForTest();

        assertTrue(productOwner.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        productOwner.disable(notificationOccurredOn);

        assertFalse(productOwner.isEnabled());
    }

    // 测试启用
    public void testEnable() throws Exception {
        ProductOwner productOwner = this.productOwnerForTest();

        productOwner.disable(this.twoHoursEarlierThanNow());

        assertFalse(productOwner.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        productOwner.enable(notificationOccurredOn);

        assertTrue(productOwner.isEnabled());
    }

    // 测试禁用时间比现在还早
    public void testDisallowEarlierDisabling() {
        ProductOwner productOwner = this.productOwnerForTest();

        productOwner.disable(this.twoHoursEarlierThanNow());

        assertFalse(productOwner.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        productOwner.enable(notificationOccurredOn);

        assertTrue(productOwner.isEnabled());

        // latent notification...
        productOwner.disable(this.twoMinutesEarlierThanNow());

        assertTrue(productOwner.isEnabled());
    }

    // 测试禁用
    public void testDisallowEarlierEnabling() {
        ProductOwner productOwner = this.productOwnerForTest();

        assertTrue(productOwner.isEnabled());

        // later...
        Date notificationOccurredOn = new Date();

        productOwner.disable(notificationOccurredOn);

        assertFalse(productOwner.isEnabled());

        // latent notification...
        productOwner.enable(this.twoMinutesEarlierThanNow());

        assertFalse(productOwner.isEnabled());
    }
}
