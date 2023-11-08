package kdt.dev.ecommerce.product.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase;
import kdt.dev.ecommerce.product.application.usecase.GetAllProductsInfoUseCase.AllProductsInfoResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Product API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductApiController {

	private final GetAllProductsInfoUseCase getAllProductsInfoUseCase;

	@Operation(summary = "모든 상품 조회 API", description = "모든 상품 정보를 조회한다")
	@ApiResponse(responseCode = "200", description = "모든 상품 정보", useReturnTypeSchema = true)
	@GetMapping
	public ResponseEntity<AllProductsInfoResponse> getAll() {
		AllProductsInfoResponse allProductsInfo = getAllProductsInfoUseCase.getAllProductsInfo();
		return ResponseEntity.ok(allProductsInfo);
	}
}
