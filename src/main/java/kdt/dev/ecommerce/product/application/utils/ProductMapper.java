package kdt.dev.ecommerce.product.application.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase.AllProductsInfoResponse;
import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase.ProductInfo;
import kdt.dev.ecommerce.product.domain.entity.Product;

@Component
public class ProductMapper {

	public AllProductsInfoResponse toAllProductsInfoResponse(List<Product> products) {
		List<ProductInfo> productInfos = products.stream()
			.map(product -> new ProductInfo(
					product.getId(),
					product.getProductName(),
					product.getDiscountAmount(),
					product.getOriginPrice(),
					product.getDiscountedPrice()
				)
			).toList();

		return new AllProductsInfoResponse(productInfos);
	}
}
