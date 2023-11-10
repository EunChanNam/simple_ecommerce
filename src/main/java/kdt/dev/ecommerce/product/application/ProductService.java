package kdt.dev.ecommerce.product.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase;
import kdt.dev.ecommerce.product.application.utils.ProductMapper;
import kdt.dev.ecommerce.product.domain.ProductRepository;
import kdt.dev.ecommerce.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements GetAllProductsInfoUseCase {

	private final ProductMapper productMapper;
	private final ProductRepository productRepository;

	@Override
	@Transactional(readOnly = true)
	public AllProductsInfoResponse getAllProductsInfo() {
		List<Product> allProducts = productRepository.findAll();
		return productMapper.toAllProductsInfoResponse(allProducts);
	}
}
