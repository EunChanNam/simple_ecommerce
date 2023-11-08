package kdt.dev.ecommerce.order.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import kdt.dev.ecommerce.order.domain.OrderRepository;
import kdt.dev.ecommerce.order.domain.entity.Order;

public interface OrderJpaRepository extends OrderRepository, JpaRepository<Order, Long> {
}
