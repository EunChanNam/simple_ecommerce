package kdt.dev.ecommerce.item.exception;

import org.springframework.http.HttpStatus;

import kdt.dev.ecommerce.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {

	;

	private final String code;
	private final HttpStatus status;
	private final String message;
}
