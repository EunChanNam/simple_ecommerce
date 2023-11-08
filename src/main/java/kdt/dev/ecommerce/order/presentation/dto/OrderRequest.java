package kdt.dev.ecommerce.order.presentation.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "주문 요청 스펙")
public record OrderRequest(
	@Schema(description = "상품 ID")
	Long productId,
	@Schema(description = "상세 아이템 ID 리스트")
	List<Long> itemDetailIds,
	@Schema(description = "주문 수량")
	int quantity
) {
}
