package kdt.dev.ecommerce.product.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import kdt.dev.ecommerce.product.domain.ProductRepository;
import kdt.dev.ecommerce.product.domain.entity.Product;

public interface ProductJpaRepository extends ProductRepository, JpaRepository<Product, Long> {

	@Override
	@EntityGraph(attributePaths = "promotion")
	Optional<Product> findWithPromotionById(Long id);
}
