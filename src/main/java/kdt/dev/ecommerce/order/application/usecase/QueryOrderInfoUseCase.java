package kdt.dev.ecommerce.order.application.usecase;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface QueryOrderInfoUseCase {

	OrderInfoResponse queryOrderInfo(Long userId, Pageable pageable);

	record OrderInfoResponse(
		List<OrderInfo> orderInfos
	) {
	}

	record OrderInfo(
		String productName,
		int quantity,
		int paymentPrice,
		List<OrderDetailInfo> orderDetailInfos
	) {
	}

	record OrderDetailInfo(
		String color,
		String size,
		String customOption
	) {
	}
}
