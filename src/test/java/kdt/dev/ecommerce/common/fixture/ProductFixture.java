package kdt.dev.ecommerce.common.fixture;

import org.springframework.test.util.ReflectionTestUtils;

import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.product.domain.entity.Promotion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductFixture {

	public static Product getProduct(int discountAmount, int originPrice) {
		return new Product(
			"product",
			"nike",
			new Promotion("promotion", discountAmount),
			originPrice
		);
	}

	public static Product getProduct() {
		return new Product(
			"product",
			"nike",
			new Promotion("promotion", 10),
			100000
		);
	}

	public static Product getProduct(Long id) {
		Product product = new Product(
			"product",
			"nike",
			new Promotion("promotion", 10),
			100000
		);
		ReflectionTestUtils.setField(product, "id", id);

		return product;
	}
}
