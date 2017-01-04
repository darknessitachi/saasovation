package org.infinite.identityaccess.domain.model.identity;

import org.infinite.identityaccess.domain.model.IdentityAccessTest;
import org.infinite.identityaccess.domain.model.identity.FullName;


/**
 * 姓名测试
 * 
 * @author Darkness
 * @date 2014-5-28 下午4:52:02
 * @version V1.0
 */
public class FullNameTest extends IdentityAccessTest {

    private final static String FIRST_NAME = "Darkness";
    private final static String LAST_NAME = "Dreamer";
    private final static String MARRIED_LAST_NAME = "Sky";
    private final static String WRONG_FIRST_NAME = "Angel";

    public FullNameTest() {
        super();
    }

    // 测试改变名
    public void testChangedFirstName() throws Exception {
        FullName name = new FullName(WRONG_FIRST_NAME, LAST_NAME);
        name = name.withChangedFirstName(FIRST_NAME);
        assertEquals(FIRST_NAME + " " + LAST_NAME, name.asFormattedName());
    }

    // 测试改变姓
    public void testChangedLastName() throws Exception {
        FullName name = new FullName(FIRST_NAME, LAST_NAME);
        name = name.withChangedLastName(MARRIED_LAST_NAME);
        assertEquals(FIRST_NAME + " " + MARRIED_LAST_NAME, name.asFormattedName());
    }

    // 测试格式化姓名
    public void testFormattedName() throws Exception {
        FullName name = new FullName(FIRST_NAME, LAST_NAME);
        assertEquals(FIRST_NAME + " " + LAST_NAME, name.asFormattedName());
    }
}
