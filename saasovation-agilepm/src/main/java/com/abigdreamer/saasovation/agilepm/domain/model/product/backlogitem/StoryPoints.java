package com.abigdreamer.saasovation.agilepm.domain.model.product.backlogitem;

/**
 * 故事点
 * 
 * @author Darkness
 * @date 2014-5-29 下午4:33:19
 * @version V1.0
 */
public enum StoryPoints {

	// 0
	ZERO {
		public int pointValue() {
			return 0;
		}
	},

	// 1
	ONE {
		public int pointValue() {
			return 1;
		}
	},

	// 1
	TWO {
		public int pointValue() {
			return 2;
		}
	},

	// 3
	THREE {
		public int pointValue() {
			return 3;
		}
	},

	// 5
	FIVE {
		public int pointValue() {
			return 5;
		}
	},

	// 8
	EIGHT {
		public int pointValue() {
			return 8;
		}
	},

	// 13
	THIRTEEN {
		public int pointValue() {
			return 13;
		}
	},

	// 20
	TWENTY {
		public int pointValue() {
			return 20;
		}
	},

	// 40
	FORTY {
		public int pointValue() {
			return 40;
		}
	},

	// 100
	ONE_HUNDRED {
		public int pointValue() {
			return 100;
		}
	};

	public abstract int pointValue();
}
