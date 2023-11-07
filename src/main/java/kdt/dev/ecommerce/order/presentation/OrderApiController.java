package kdt.dev.ecommerce.order.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kdt.dev.ecommerce.global.annotation.Login;
import kdt.dev.ecommerce.global.resolver.dto.LoginInfo;
import kdt.dev.ecommerce.order.application.usecase.OrderUseCase;
import kdt.dev.ecommerce.order.presentation.dto.OrderRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderApiController {

	private final OrderUseCase orderUseCase;
	private final Mapper mapper;

	@PostMapping
	public ResponseEntity<Boolean> order(
		@Login LoginInfo loginInfo,
		@RequestBody OrderRequest request
	) {
		orderUseCase.order(mapper.toOrderCommand(request, loginInfo.userId()));

		return ResponseEntity.ok(true);
	}
}
