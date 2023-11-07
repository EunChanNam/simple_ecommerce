package kdt.dev.ecommerce.order.application.usecase;

public interface OrderUseCase {

	Long order(OrderCommand command);

	record OrderCommand(
		Long userId,
		Long productId,
		Long itemDetailId,
		int quantity
	) {
	}
}

