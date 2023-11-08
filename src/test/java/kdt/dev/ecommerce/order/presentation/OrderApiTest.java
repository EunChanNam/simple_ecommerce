package kdt.dev.ecommerce.order.presentation;

import static kdt.dev.ecommerce.common.fixture.AuthFixture.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import kdt.dev.ecommerce.common.support.ApiTestSupport;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;
import kdt.dev.ecommerce.user.domain.User;
import kdt.dev.ecommerce.user.domain.UserRepository;
import kdt.dev.ecommerce.user.fixture.UserFixture;

@DisplayName("[OrderApi 테스트]")
@Sql(value = "/sql/dummy.sql")
@Transactional
class OrderApiTest extends ApiTestSupport {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("[주문 API 를 호출한다]")
	void order() throws Exception {
		//given
		Long userId = userRepository.save(UserFixture.getUser()).getId();

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

	@Test
	@DisplayName("[주문 조회 API 를 호출한다]")
	void queryOrderInfo() throws Exception {
		//given
		User user = userRepository.save(UserFixture.getUser());


		//when
		ResultActions actual = mockMvc.perform(
			MockMvcRequestBuilders
				.get("/api/v1/orders")
				.header(AUTHORIZATION, BEARER + getAccessToken(user.getId()))
		);

		//then
		actual.andExpectAll(
			status().isOk(),
			jsonPath("$.orderInfos.size()").value(2),
			jsonPath("$.orderInfos[0].orderDetailInfos.size()").value(1)
		);
	}
}