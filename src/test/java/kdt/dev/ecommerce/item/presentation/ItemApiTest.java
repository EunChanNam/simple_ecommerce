package kdt.dev.ecommerce.item.presentation;

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

import kdt.dev.ecommerce.common.fixture.ItemDetailFixture;
import kdt.dev.ecommerce.common.support.ApiTestSupport;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.item.infrastructure.ItemDetailJpaRepository;

@DisplayName("[ItemApi 테스트]")
class ItemApiTest extends ApiTestSupport {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ItemDetailJpaRepository itemDetailRepository;

	@Test
	@DisplayName("[특정 상품의 아이템 정보 조회 API 를 호출한다]")
	void getItemInfo() throws Exception {
		//given
		List<ItemDetail> itemDetails = List.of(
			ItemDetailFixture.getItemDetail(),
			ItemDetailFixture.getItemDetail()
		);

		given(itemDetailRepository.findByProductId(1L))
			.willReturn(itemDetails);

		//when
		ResultActions actual = mockMvc.perform(
			MockMvcRequestBuilders
				.get("/api/v1/items/1")
		);

		//then
		actual.andExpectAll(
			status().isOk(),
			jsonPath("$.itemInfos.size()").value(2)
		);
	}
}