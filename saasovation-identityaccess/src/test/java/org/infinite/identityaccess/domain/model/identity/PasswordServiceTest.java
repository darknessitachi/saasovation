package org.infinite.identityaccess.domain.model.identity;

import org.infinite.identityaccess.domain.DomainRegistry;
import org.infinite.identityaccess.domain.model.IdentityAccessTest;


/**
 * 密码服务测试
 * 
 * @author Darkness
 * @date 2014-5-27 下午10:42:11
 * @version V1.0
 */
public class PasswordServiceTest extends IdentityAccessTest {

    public PasswordServiceTest() {
        super();
    }

    // 测试生成强密码
    public void testGenerateStrongPassword() throws Exception {
        String password =
                DomainRegistry
                    .passwordService()
                    .generateStrongPassword();

        assertTrue(DomainRegistry.passwordService().isStrong(password));
        assertFalse(DomainRegistry.passwordService().isWeak(password));
    }

    // 测试是否强密码
    public void testIsStrongPassword() throws Exception {
        final String password = "Th1sShudBStrong.";
        assertTrue(DomainRegistry.passwordService().isStrong(password));
        assertFalse(DomainRegistry.passwordService().isVeryStrong(password));
        assertFalse(DomainRegistry.passwordService().isWeak(password));
    }

	// 测试是否超强密码
    public void testIsVeryStrongPassword() throws Exception {
        final String password = "Th1sSh0uldBV3ryStrong!";
        assertTrue(DomainRegistry.passwordService().isVeryStrong(password));
        assertTrue(DomainRegistry.passwordService().isStrong(password));
        assertFalse(DomainRegistry.passwordService().isWeak(password));
    }

	// 测试是否弱密码
    public void testIsWeakPassword() throws Exception {
        final String password = "Weakness";
        assertFalse(DomainRegistry.passwordService().isVeryStrong(password));
        assertFalse(DomainRegistry.passwordService().isStrong(password));
        assertTrue(DomainRegistry.passwordService().isWeak(password));
    }
}
