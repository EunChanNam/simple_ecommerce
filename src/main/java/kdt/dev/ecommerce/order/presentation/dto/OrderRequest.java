package kdt.dev.ecommerce.order.presentation.dto;

import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;

public record OrderRequest(
	Long productId,
	Long itemDetailId,
	int quantity
) {
	public OrderUseCase.OrderCommand toOrderCommand(Long userId) {
		return new OrderUseCase.OrderCommand(
			userId,
			productId(),
			itemDetailId(),
			quantity()
		);
	}
}
