package kdt.dev.ecommerce.item.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import kdt.dev.ecommerce.common.fixture.ItemDetailFixture;
import kdt.dev.ecommerce.common.support.MockTestSupport;
import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.item.exception.ItemErrorCode;

@DisplayName("[ItemDetailFindService 테스트]")
class ItemDetailFindServiceTest extends MockTestSupport {

	@InjectMocks
	private ItemDetailFindService itemDetailFindService;
	@Mock
	private ItemDetailRepository itemDetailRepository;

	@Nested
	@DisplayName("[ID 로 Item 정보와 함께 ItemDetail 을 조회한다]")
	class getItemDetailWithItemById {

		@Test
		@DisplayName("[성공적으로 조회한다]")
		void success() {
			//given
			ItemDetail itemDetail = ItemDetailFixture.getItemDetail(1000, 100, 3);
			given(itemDetailRepository.findWithItemById(1L))
				.willReturn(Optional.of(itemDetail));

			//when
			ItemDetail actual = itemDetailFindService.getItemDetailWithItemById(1L);

			//then
			assertThat(actual).isEqualTo(itemDetail);
		}

		@Test
		@DisplayName("[ID 에 해당하는 ItemDetail 이 없어서 실패한다]")
		void fail() {
			//given
			given(itemDetailRepository.findWithItemById(1L))
				.willReturn(Optional.empty());

			//when
			ThrowingCallable when = () -> itemDetailFindService.getItemDetailWithItemById(1L);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(ItemErrorCode.NOT_FOUND.getMessage());
		}
	}
}