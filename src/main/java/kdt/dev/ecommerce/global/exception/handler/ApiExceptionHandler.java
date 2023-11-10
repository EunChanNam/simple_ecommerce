package kdt.dev.ecommerce.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.global.exception.ErrorCode;
import kdt.dev.ecommerce.global.exception.dto.ErrorResponse;
import kdt.dev.ecommerce.global.exception.dto.StockNotEnoughResponse;
import kdt.dev.ecommerce.item.exception.StockNotEnoughException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.error("파라미터 Validation 예외 : ", ex);

		if (ex.getFieldError() != null) {
			return new ErrorResponse("M-001", ex.getFieldError().getDefaultMessage());
		}
		return new ErrorResponse("M-002", "올바르지 않은 요청 파라미터입니다");
	}

	@ExceptionHandler(CommerceException.class)
	public ResponseEntity<ErrorResponse> handleCommerceException(CommerceException ex) {
		log.error("커스텀 예외 : ", ex);

		ErrorCode errorCode = ex.getErrorCode();
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(new ErrorResponse(errorCode));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(StockNotEnoughException.class)
	public StockNotEnoughResponse handleStockNotEnoughException(StockNotEnoughException ex) {
		log.error("커스텀 예외 : ", ex);

		return new StockNotEnoughResponse(
			"재고가 부족합니다",
			ex.getItemName(),
			ex.getColor(),
			ex.getSize(),
			ex.getCustomOption()
		);
	}
}
