package kdt.dev.ecommerce.product.domain;

import java.util.Optional;

import kdt.dev.ecommerce.product.domain.entity.Product;

public interface ProductRepository {

	Optional<Product> findWithPromotionById(Long id);
}
