package kdt.dev.ecommerce.order.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;

import kdt.dev.ecommerce.order.domain.entity.Order;

public interface OrderRepository {

	Order save(Order order);

	List<Order> findByUserId(Long userId, Pageable pageable);
}
