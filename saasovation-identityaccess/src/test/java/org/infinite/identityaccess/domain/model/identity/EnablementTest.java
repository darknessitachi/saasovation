package org.infinite.identityaccess.domain.model.identity;

import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.Enablement;


/**
 * 可用测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午4:55:43
 * @version V1.0
 */
public class EnablementTest extends IdentityAccessTest {

    public EnablementTest() {
        super();
    }

    // 测试可用启用
    public void testEnablementEnabled() throws Exception {

        Enablement enablement = new Enablement(true, null, null);

        assertTrue(enablement.isEnablementEnabled());
    }

    // 测试可用禁用
    public void testEnablementDisabled() throws Exception {

        Enablement enablement = new Enablement(false, null, null);

        assertFalse(enablement.isEnablementEnabled());
    }

    // 测试可用超出日期
    public void testEnablementOutsideStartEndDates() throws Exception {

        Enablement enablement =
            new Enablement(
                    true,
                    this.dayBeforeYesterday(),
                    this.yesterday());

        assertFalse(enablement.isEnablementEnabled());
    }

    // 测试可用日期不连续
    public void testEnablementUnsequencedDates() throws Exception {

        boolean failure = false;

        try {
            new Enablement(
                    true,
                    this.tomorrow(),
                    this.today());
        } catch (Throwable t) {
            failure = true;
        }

        assertTrue(failure);
    }

    // 测试可用日期超时
    public void testEnablementEndsTimeExpired() throws Exception {

        Enablement enablement =
            new Enablement(
                    true,
                    this.dayBeforeYesterday(),
                    this.yesterday());

        assertTrue(enablement.isTimeExpired());
    }

    // 测试可用日期还没到
    public void testEnablementHasNotBegunTimeExpired() throws Exception {

        Enablement enablement =
            new Enablement(
                    true,
                    this.tomorrow(),
                    this.dayAfterTomorrow());

        assertTrue(enablement.isTimeExpired());
    }
}
