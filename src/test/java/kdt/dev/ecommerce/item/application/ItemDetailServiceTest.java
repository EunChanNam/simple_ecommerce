package kdt.dev.ecommerce.item.application;

import static kdt.dev.ecommerce.item.application.usecase.GetItemInfoUseCase.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import kdt.dev.ecommerce.common.fixture.ItemDetailFixture;
import kdt.dev.ecommerce.common.support.MockTestSupport;
import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;

@DisplayName("[ItemDetailService 테스트]")
class ItemDetailServiceTest extends MockTestSupport {

	@InjectMocks
	private ItemDetailService itemDetailService;
	@Mock
	private ItemDetailRepository itemDetailRepository;

	@Test
	@DisplayName("[상품 아이디로 Item 과 ItemDetail 정보를 조회한다]")
	void getItemInfo() {
		//given
		List<ItemDetail> itemDetails = List.of(
			ItemDetailFixture.getItemDetail(),
			ItemDetailFixture.getItemDetail()
		);

		given(itemDetailRepository.findByProductId(1L))
			.willReturn(itemDetails);

		//when
		ItemInfoResponse actual = itemDetailService.getItemInfo(1L);

		//then
		List<ItemInfo> itemInfos = actual.itemInfos();
		assertThat(itemInfos).hasSize(2);

		for (int i = 0; i < itemInfos.size(); i++) {
			ItemInfo itemInfo = itemInfos.get(i);
			ItemDetail itemDetail = itemDetails.get(i);

			ItemDetailInfo itemDetailInfo = itemInfo.itemDetailInfo();
			assertAll(
				() -> assertThat(itemInfo.itemName()).isEqualTo(itemDetail.getItemName()),
				() -> assertThat(itemDetailInfo.color()).isEqualTo(itemDetail.getColor()),
				() -> assertThat(itemDetailInfo.size()).isEqualTo(itemDetail.getSize()),
				() -> assertThat(itemDetailInfo.stock()).isEqualTo(itemDetail.getStock()),
				() -> assertThat(itemDetailInfo.customOption()).isEqualTo(itemDetail.getCustomOption()),
				() -> assertThat(itemDetailInfo.changeAmount()).isEqualTo(itemDetail.getChangeAmount())
			);
		}
	}
}