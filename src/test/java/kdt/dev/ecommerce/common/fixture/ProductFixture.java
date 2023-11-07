package kdt.dev.ecommerce.common.fixture;

import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.product.domain.entity.Promotion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductFixture {

	public static Product getProduct(int discountAmount) {
		return new Product(
			"product",
			"nike",
			new Promotion("promotion", discountAmount)
		);
	}
}
