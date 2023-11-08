package kdt.dev.ecommerce.product.domain;

import java.util.List;
import java.util.Optional;

import kdt.dev.ecommerce.product.domain.entity.Product;

public interface ProductRepository {

	List<Product> findAll();
	Optional<Product> findWithPromotionById(Long id);
}
