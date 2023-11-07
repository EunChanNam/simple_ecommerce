package kdt.dev.ecommerce.order.presentation.dto;

public record OrderRequest(
	Long productId,
	Long itemDetailId,
	int quantity
) {
}
