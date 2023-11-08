package kdt.dev.ecommerce.item.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kdt.dev.ecommerce.item.application.usecase.GetItemInfoUseCase;
import kdt.dev.ecommerce.item.application.usecase.GetItemInfoUseCase.ItemInfoResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Item API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemApiController {

	private final GetItemInfoUseCase getItemInfoUseCase;

	@Operation(summary = "특정 상품 아이템 정보 조회 API", description = "특정 상품의 아이템 정보를 조회한다")
	@ApiResponse(responseCode = "200", description = "상품의 아이템 정보", useReturnTypeSchema = true)
	@GetMapping("/{productId}")
	public ResponseEntity<ItemInfoResponse> getItemInfo(
		@Parameter(description = "상품 아이디") @PathVariable Long productId
	) {
		ItemInfoResponse itemInfoResponse = getItemInfoUseCase.getItemInfo(productId);
		return ResponseEntity.ok(itemInfoResponse);
	}
}
