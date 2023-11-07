package kdt.dev.ecommerce.product.application;

import org.springframework.stereotype.Service;

import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.product.domain.ProductRepository;
import kdt.dev.ecommerce.product.domain.entity.Product;
import kdt.dev.ecommerce.product.exception.ProductErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductFindService {

	private final ProductRepository productRepository;

	public Product getProductWithPromotionById(Long id) {
		return productRepository.findWithPromotionById(id)
			.orElseThrow(() -> new CommerceException(ProductErrorCode.NOT_FOUND));
	}
}
