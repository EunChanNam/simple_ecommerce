package kdt.dev.ecommerce.product.application;

import java.util.List;

import org.springframework.stereotype.Service;

import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase;
import kdt.dev.ecommerce.product.application.utils.Mapper;
import kdt.dev.ecommerce.product.domain.ProductRepository;
import kdt.dev.ecommerce.product.domain.entity.Product;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements GetAllProductsInfoUseCase {

	private final Mapper mapper;
	private final ProductRepository productRepository;

	@Override
	public AllProductsInfoResponse getAllProductsInfo() {
		List<Product> allProducts = productRepository.findAll();
		return mapper.toAllProductsInfoResponse(allProducts);
	}
}
