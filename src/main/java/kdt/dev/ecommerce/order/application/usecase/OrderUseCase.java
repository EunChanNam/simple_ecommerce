package kdt.dev.ecommerce.order.application.usecase;

import java.util.List;

public interface OrderUseCase {

	Long order(OrderCommand command);

	record OrderCommand(
		Long userId,
		Long productId,
		List<Long> itemDetailIds,
		int quantity
	) {
	}
}

