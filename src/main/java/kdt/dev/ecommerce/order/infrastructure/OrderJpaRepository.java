package kdt.dev.ecommerce.order.infrastructure;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kdt.dev.ecommerce.order.domain.OrderRepository;
import kdt.dev.ecommerce.order.domain.entity.Order;

public interface OrderJpaRepository extends OrderRepository, JpaRepository<Order, Long> {

	@EntityGraph(attributePaths = {"user", "product"})
	List<Order> findByUserId(Long userId, Pageable pageable);
}
