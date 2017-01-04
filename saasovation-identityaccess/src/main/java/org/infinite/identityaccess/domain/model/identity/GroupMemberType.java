package org.infinite.identityaccess.domain.model.identity;

/**
 * 组成员类型
 * 
 * @author Darkness
 * @date 2014-5-27 下午5:43:15
 * @version V1.0
 */
public enum GroupMemberType {

	// 组
    Group {
        public boolean isGroup() {
            return true;
        }
    },

    // 用户
    User {
        public boolean isUser() {
            return true;
        }
    };

    public boolean isGroup() {
        return false;
    }

    public boolean isUser() {
        return false;
    }
}
