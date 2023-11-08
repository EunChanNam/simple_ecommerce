package kdt.dev.ecommerce.order.application.utils;

import java.util.List;

import kdt.dev.ecommerce.item.domain.entity.ItemDetail;
import kdt.dev.ecommerce.order.application.usecase.QueryOrderInfoUseCase.OrderDetailInfo;
import kdt.dev.ecommerce.order.application.usecase.QueryOrderInfoUseCase.OrderInfo;
import kdt.dev.ecommerce.order.application.usecase.QueryOrderInfoUseCase.OrderInfoResponse;
import kdt.dev.ecommerce.order.domain.entity.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderMapper {

	public static OrderInfoResponse toOrderInfoResponse(List<Order> orders) {
		List<OrderInfo> orderInfos = orders.stream()
			.map(order -> {
				List<OrderDetailInfo> orderDetailInfos = order.getOrderDetails()
					.stream()
					.map(orderDetail -> {
						ItemDetail itemDetail = orderDetail.getItemDetail();
						return new OrderDetailInfo(
							itemDetail.getColor(),
							itemDetail.getSize(),
							itemDetail.getCustomOption()
						);
					}).toList();

				return new OrderInfo(
					order.getProductName(),
					order.getQuantity(),
					order.getTotalPrice(),
					orderDetailInfos
				);
			}).toList();

		return new OrderInfoResponse(orderInfos);
	}
}
