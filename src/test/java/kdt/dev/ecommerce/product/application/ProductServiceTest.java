package kdt.dev.ecommerce.product.application;

import static kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import kdt.dev.ecommerce.common.fixture.ProductFixture;
import kdt.dev.ecommerce.product.application.utils.Mapper;
import kdt.dev.ecommerce.product.domain.ProductRepository;
import kdt.dev.ecommerce.product.domain.entity.Product;

@DisplayName("[ProductService 테스트]")
class ProductServiceTest {

	private final ProductService productService;
	private final ProductRepository productRepository;

	public ProductServiceTest() {
		this.productRepository = Mockito.mock(ProductRepository.class);
		this.productService = new ProductService(new Mapper(), productRepository);
	}

	@Test
	@DisplayName("[모든 Product 정보를 조회한다]")
	void getAllProductsInfo() {
		//given
		List<Product> products = List.of(ProductFixture.getProduct(1L), ProductFixture.getProduct(2L));
		given(productRepository.findAll())
			.willReturn(products);

		//when
		AllProductsInfoResponse actual = productService.getAllProductsInfo();

		//then
		List<ProductInfo> result = actual.result();
		assertThat(result).hasSize(2);

		for (int i = 0; i < result.size(); i++) {
			ProductInfo productInfo = result.get(i);
			Product product = products.get(i);

			assertAll(
				() -> assertThat(productInfo.productId()).isEqualTo(product.getId()),
				() -> assertThat(productInfo.productName()).isEqualTo(product.getProductName()),
				() -> assertThat(productInfo.discountAmount()).isEqualTo(product.getDiscountAmount()),
				() -> assertThat(productInfo.originPrice()).isEqualTo(product.getOriginPrice()),
				() -> assertThat(productInfo.discountedPrice()).isEqualTo(product.getDiscountedPrice())
			);
		}
	}
}