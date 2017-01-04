package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

/**
 * 待办项类别
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:45:21
 * @version V1.0
 */
public enum BacklogItemType  {

	// 特征
    FEATURE {
        public boolean isFeature() {
            return true;
        }
    },

    // 改善
    ENHANCEMENT {
        public boolean isEnhancement() {
            return true;
        }
    },

    // 缺陷
    DEFECT {
        public boolean isDefect() {
            return true;
        }
    },

    // 基础
    FOUNDATION {
        public boolean isFoundation() {
            return true;
        }
    },

    // 整合
    INTEGRATION {
        public boolean isIntegration() {
            return true;
        }
    };

    public boolean isDefect() {
        return false;
    }

    public boolean isEnhancement() {
        return false;
    }

    public boolean isFeature() {
        return false;
    }

    public boolean isFoundation() {
        return false;
    }

    public boolean isIntegration() {
        return false;
    }
}
