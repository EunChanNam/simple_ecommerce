package kdt.dev.ecommerce.product.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase;
import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase.AllProductsInfoResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {

	private final GetAllProductsInfoUseCase getAllProductsInfoUseCase;

	@GetMapping
	public ResponseEntity<AllProductsInfoResponse> getAll() {
		AllProductsInfoResponse allProductsInfo = getAllProductsInfoUseCase.getAllProductsInfo();
		return ResponseEntity.ok(allProductsInfo);
	}
}
