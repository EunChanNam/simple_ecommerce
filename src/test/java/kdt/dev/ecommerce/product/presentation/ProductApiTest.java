package kdt.dev.ecommerce.product.presentation;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kdt.dev.ecommerce.common.fixture.ProductFixture;
import kdt.dev.ecommerce.common.support.ApiTestSupport;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.product.infrastructure.ProductJpaRepository;

@DisplayName("ProductApi 테스트")
class ProductApiTest extends ApiTestSupport {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ProductJpaRepository productRepository;

	@Test
	@DisplayName("[모든 상품 정보 조회 API 를 호출한다]")
	void getAll() throws Exception {
		//given
		List<Product> products = List.of(ProductFixture.getProduct(1L), ProductFixture.getProduct(2L));
		given(productRepository.findAll())
			.willReturn(products);

		//when
		ResultActions actual = mockMvc.perform(
			MockMvcRequestBuilders
				.get("/api/v1/products")
		);

		//then
		actual.andExpectAll(
			status().isOk(),
			jsonPath("$.result.size()").value(2)
		);
	}
}