package kdt.dev.ecommerce.product.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import kdt.dev.ecommerce.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductErrorCode implements ErrorCode {

	NOT_FOUND("P_001", BAD_REQUEST, "존재하지 않는 Product 입니다"),
	;

	private final String code;
	private final HttpStatus status;
	private final String message;
}
