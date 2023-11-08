package kdt.dev.ecommerce.order.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kdt.dev.ecommerce.global.annotation.Login;
import kdt.dev.ecommerce.global.resolver.dto.LoginInfo;
import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderApiController {

	private final OrderUseCase orderUseCase;
	private final Mapper mapper;

	@Operation(summary = "주문 API", description = "주문을 한다")
	@ApiResponse(responseCode = "200", description = "주문 성공")
	@PostMapping
	public ResponseEntity<Boolean> order(
		@Parameter(hidden = true) @Login LoginInfo loginInfo,
		@RequestBody OrderRequest request
	) {
		orderUseCase.order(mapper.toOrderCommand(request, loginInfo.userId()));

		return ResponseEntity.ok(true);
	}
}
