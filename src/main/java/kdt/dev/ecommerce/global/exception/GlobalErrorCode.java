package kdt.dev.ecommerce.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

	;

	private final String code;
	private final HttpStatus status;
	private final String message;
}
