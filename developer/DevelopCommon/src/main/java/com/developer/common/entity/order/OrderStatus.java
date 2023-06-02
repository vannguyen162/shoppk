package com.developer.common.entity.order;

public enum OrderStatus {
	NEW {
		@Override
		public String defaultDescription() {
			return "Đặt hàng bởi khách hàng";
		}
	},
	CANCELLED {
		@Override
		public String defaultDescription() {
			return "Đơn hàng đã bị hủy bỏ";
		}
	},
	PROCESSING {
		@Override
		public String defaultDescription() {
			return "Đơn hàng đang được xử lý";
		}
	},
	PACKAGED {
		@Override
		public String defaultDescription() {
			return "Đơn hàng đang được đóng gói";
		}
	},
	PICKED {
		@Override
		public String defaultDescription() {
			return "Shipper đang giữ hàng và gửi đến khách hàng";
		}
	},
	SHIPPNG {
		@Override
		public String defaultDescription() {
			return "Đơn hàng đang được giao tới khách hàng";
		}
	},
	DELIVERD {
		@Override
		public String defaultDescription() {
			return "Khách hàng đã nhận đơn hàng";
		}
	},
	RETURN_REQUESTED {
		@Override
		public String defaultDescription() {
			return "Khách hàng gửi yêu cầu đổi trả hàng đã mua";
		}		
	},
	RETURNED {
		@Override
		public String defaultDescription() {
			return "Sản phẩm đã được trả lại";
		}
	},
	PAID {
		@Override
		public String defaultDescription() {
			return "Khách hàng đã trả đơn hàng";
		}
	},
	REFUNDED {
		@Override
		public String defaultDescription() {
			return "Khách hàng hoàn trả đơn hàng";
		}
	};

	public abstract String defaultDescription();
}
