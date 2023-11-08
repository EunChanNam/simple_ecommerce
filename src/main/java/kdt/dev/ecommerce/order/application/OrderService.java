package kdt.dev.ecommerce.order.application;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kdt.dev.ecommerce.item.domain.ItemDetailRepository;
import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;
import kdt.dev.ecommerce.order.application.usecase.QueryOrderInfoUseCase;
import kdt.dev.ecommerce.order.application.utils.OrderMapper;
import kdt.dev.ecommerce.order.domain.OrderRepository;
import kdt.dev.ecommerce.order.domain.entity.Order;
import kdt.dev.ecommerce.product.application.ProductFindService;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.user.application.UserFindService;
import kdt.dev.ecommerce.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements OrderUseCase, QueryOrderInfoUseCase {

	private final OrderRepository orderRepository;
	private final UserFindService userFindService;
	private final ItemDetailRepository itemDetailRepository;
	private final ProductFindService productFindService;

	@Override
	@Transactional
	public Long order(OrderCommand command) {
		User user = userFindService.getById(command.userId());
		Product product = productFindService.getProductWithPromotionById(command.productId());
		List<ItemDetail> itemDetails = itemDetailRepository.findWithByIdIn(command.itemDetailIds());

		Order order = new Order(
			user,
			product,
			command.quantity(),
			itemDetails
		);

		return orderRepository.save(order).getId();
	}

	@Override
	@Transactional(readOnly = true)
	public OrderInfoResponse queryOrderInfo(Long userId, Pageable pageable) {
		List<Order> orders = orderRepository.findByUserId(userId, pageable);
		return OrderMapper.toOrderInfoResponse(orders);
	}
}
