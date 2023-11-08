package kdt.dev.ecommerce.order.presentation;

import org.springframework.stereotype.Component;

import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;

@Component
public class Mapper {

	public OrderUseCase.OrderCommand toOrderCommand(
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
