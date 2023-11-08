package kdt.dev.ecommerce.product.application.usecase;

import java.util.List;

public interface GetAllProductsInfoUseCase {

	AllProductsInfoResponse getAllProductsInfo();

	record AllProductsInfoResponse(
		List<ProductInfo> result
	) {
	}

	record ProductInfo(
		Long productId,
		String productName,
		double discountAmount,
		int originPrice,
		int discountedPrice
	) {
	}
}
