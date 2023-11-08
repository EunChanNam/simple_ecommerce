package kdt.dev.ecommerce.order.presentation;

import static kdt.dev.ecommerce.common.fixture.AuthFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kdt.dev.ecommerce.common.fixture.ItemDetailFixture;
import kdt.dev.ecommerce.common.fixture.ProductFixture;
import kdt.dev.ecommerce.common.support.ApiTestSupport;
import kdt.dev.ecommerce.item.application.ItemDetailFindService;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;
import kdt.dev.ecommerce.product.application.ProductFindService;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.user.domain.UserRepository;
import kdt.dev.ecommerce.user.fixture.UserFixture;

@DisplayName("[OrderApi 테스트]")
class OrderApiTest extends ApiTestSupport {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;

	//추후에 Promotion, Item Repository 를 만들면 실제 의존성으로 테스트하도록 변경
	@MockBean
	private ProductFindService productFindService;
	@MockBean
	private ItemDetailFindService itemDetailFindService;

	private void setId(Object object) {
		ReflectionTestUtils.setField(object, "id", 1L);
	}

	@Test
	@DisplayName("[주문 API 를 호출한다]")
	void order() throws Exception {
		//given
		Long userId = userRepository.save(UserFixture.getUser()).getId();

		//추후에 Promotion, Item Repository 를 만들면 실제 의존성으로 테스트하도록 변경
		//===================================================================
		Product product = ProductFixture.getProduct(10, 20000);
		setId(product);
		given(productFindService.getProductWithPromotionById(1L))
			.willReturn(product);

		ItemDetail itemDetail = ItemDetailFixture.getItemDetail(20000, 1000, 10);
		setId(itemDetail);
		setId(itemDetail.getItem());
		given(itemDetailFindService.getItemDetailWithItemById(1L))
			.willReturn(itemDetail);
		//===================================================================

		OrderRequest orderRequest = new OrderRequest(1L, List.of(1L), 10);

		//when
		ResultActions actual = mockMvc.perform(
			MockMvcRequestBuilders
				.post("/api/v1/orders")
				.header(AUTHORIZATION, BEARER + getAccessToken(userId))
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(orderRequest))
		);

		//then
		actual.andExpectAll(
			status().isOk(),
			content().string(String.valueOf(true))
		);
	}
}