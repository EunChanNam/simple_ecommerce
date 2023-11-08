package kdt.dev.ecommerce.order.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import kdt.dev.ecommerce.order.application.usecase.QueryOrderInfoUseCase;
import kdt.dev.ecommerce.order.application.usecase.QueryOrderInfoUseCase.OrderInfoResponse;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;
import kdt.dev.ecommerce.order.presentation.utils.OrderMapper;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderApiController {

	private final OrderUseCase orderUseCase;
	private final QueryOrderInfoUseCase queryOrderInfoUseCase;

	@Operation(summary = "주문 API", description = "주문을 한다")
	@ApiResponse(responseCode = "200", description = "주문 성공")
	@PostMapping
	public ResponseEntity<Boolean> order(
		@Parameter(hidden = true) @Login LoginInfo loginInfo,
		@RequestBody OrderRequest request
	) {
		orderUseCase.order(OrderMapper.toOrderCommand(request, loginInfo.userId()));

		return ResponseEntity.ok(true);
	}

	@Operation(summary = "주문 조회 API", description = "주문")
	@ApiResponse(responseCode = "200", description = "주문 성공", useReturnTypeSchema = true)
	@GetMapping
	public ResponseEntity<OrderInfoResponse> queryOrderInfo(
		@Parameter(hidden = true) @Login LoginInfo loginInfo,
		@Parameter(description = "페이징 정보") Pageable pageable
	) {
		OrderInfoResponse orderInfoResponse = queryOrderInfoUseCase
			.queryOrderInfo(loginInfo.userId(), pageable);

		return ResponseEntity.ok(orderInfoResponse);
	}
}
