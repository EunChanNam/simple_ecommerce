package kdt.dev.ecommerce.user.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import kdt.dev.ecommerce.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

	NOT_FOUND("U_001", BAD_REQUEST, "존재하지 않는 User 입니다"),
	;

	private final String code;
	private final HttpStatus status;
	private final String message;
}
