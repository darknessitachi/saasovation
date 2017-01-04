package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

/**
 * 任务状态
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:43:34
 * @version V1.0
 */
public enum TaskStatus  {

	// 未开始
    NOT_STARTED {
        public boolean isNotStarted() {
            return true;
        }
    },

    // 处理中
    IN_PROGRESS {
        public boolean isInProgress() {
            return true;
        }
    },

    // 有阻碍
    IMPEDED {
        public boolean isImpeded() {
            return true;
        }
    },

    // 完成
    DONE {
        public boolean isDone() {
            return true;
        }
    };

    public boolean isDone() {
        return false;
    }

    public boolean isImpeded() {
        return false;
    }

    public boolean isInProgress() {
        return false;
    }

    public boolean isNotStarted() {
        return false;
    }
}
