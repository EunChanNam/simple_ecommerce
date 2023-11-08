package kdt.dev.ecommerce.order.application;

import org.springframework.stereotype.Service;

import kdt.dev.ecommerce.item.application.ItemDetailFindService;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;
import kdt.dev.ecommerce.order.domain.OrderRepository;
import kdt.dev.ecommerce.order.domain.entity.Order;
import kdt.dev.ecommerce.product.application.ProductFindService;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.user.application.UserFindService;
import kdt.dev.ecommerce.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase {

	private final OrderRepository orderRepository;
	private final UserFindService userFindService;
	private final ItemDetailFindService itemDetailFindService;
	private final ProductFindService productFindService;

	@Override
	public Long order(OrderCommand command) {
		User user = userFindService.getById(command.userId());
		Product product = productFindService.getProductWithPromotionById(command.productId());
		ItemDetail itemDetail = itemDetailFindService.getItemDetailWithItemById(command.itemDetailId());

		Order order = Order.of(
			user,
			product,
			itemDetail,
			command.quantity()
		);

		return orderRepository.save(order).getId();
	}
}
