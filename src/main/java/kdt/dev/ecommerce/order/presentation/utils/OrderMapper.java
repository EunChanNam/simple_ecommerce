package kdt.dev.ecommerce.order.presentation.utils;

import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderMapper {

	public static OrderUseCase.OrderCommand toOrderCommand(
		OrderRequest request,
		Long userId
	) {
		return new OrderUseCase.OrderCommand(
			userId,
			request.productId(),
			request.itemDetailIds(),
			request.quantity()
		);
	}
}
