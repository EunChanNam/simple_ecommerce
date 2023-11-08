package kdt.dev.ecommerce.order.application;

import static kdt.dev.ecommerce.order.application.usecase.OrderUseCase.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import kdt.dev.ecommerce.common.fixture.ItemDetailFixture;
import kdt.dev.ecommerce.common.fixture.ProductFixture;
import kdt.dev.ecommerce.common.support.MockTestSupport;
import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.order.domain.OrderRepository;
import kdt.dev.ecommerce.order.domain.entity.Order;
import kdt.dev.ecommerce.product.application.ProductFindService;
import kdt.dev.ecommerce.user.application.UserFindService;
import kdt.dev.ecommerce.user.fixture.UserFixture;

@DisplayName("[OrderService 테스트]")
class OrderServiceTest extends MockTestSupport {

	@InjectMocks
	private OrderService orderService;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private UserFindService userFindService;
	@Mock
	private ItemDetailRepository itemDetailRepository;
	@Mock
	private ProductFindService productFindService;

	@Test
	@DisplayName("[주문을 한다]")
	void order() {
		//given
		given(itemDetailRepository.findWithByIdIn(anyList()))
			.willReturn(List.of(ItemDetailFixture.getItemDetail()));

		given(productFindService.getProductWithPromotionById(1L))
			.willReturn(ProductFixture.getProduct());

		given(userFindService.getById(1L))
			.willReturn(UserFixture.getUser());

		Order order = new Order();
		ReflectionTestUtils.setField(order, "id", 1L);
		given(orderRepository.save(any(Order.class)))
			.willReturn(order);

		OrderCommand orderCommand = new OrderCommand(1L, 1L, List.of(1L), 10);

		//when
		Long actual = orderService.order(orderCommand);

		//then
		assertThat(actual).isEqualTo(order.getId());
	}
}