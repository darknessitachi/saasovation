package com.abigdreamer.saasovation.agilepm.domain.model.discussion;

/**
 * 讨论可用状态
 * 
 * @author Darkness
 * @date 2014-5-29 下午3:23:37
 * @version V1.0
 */
public enum DiscussionAvailability {

	// 不可用
	ADD_ON_NOT_ENABLED {
		public boolean isAddOnNotAvailable() {
			return true;
		}
	},

	// 失败
	FAILED {
		public boolean isFailed() {
			return true;
		}
	},

	// 未请求
	NOT_REQUESTED {
		public boolean isNotRequested() {
			return true;
		}
	},

	// 请求
	REQUESTED {
		public boolean isRequested() {
			return true;
		}
	},

	// 准备完毕
	READY {
		public boolean isReady() {
			return true;
		}
	};

	public boolean isAddOnNotAvailable() {
		return false;
	}

	public boolean isFailed() {
		return false;
	}

	public boolean isNotRequested() {
		return false;
	}

	public boolean isReady() {
		return false;
	}

	public boolean isRequested() {
		return false;
	}
}
