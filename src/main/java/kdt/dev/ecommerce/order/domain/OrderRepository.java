package kdt.dev.ecommerce.order.domain;

import kdt.dev.ecommerce.order.domain.entity.Order;

public interface OrderRepository {

	Order save(Order order);
}
