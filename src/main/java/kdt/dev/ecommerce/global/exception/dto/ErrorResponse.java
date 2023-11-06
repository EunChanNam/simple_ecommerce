package kdt.dev.ecommerce.global.exception.dto;

import java.time.LocalDateTime;

import kdt.dev.ecommerce.global.exception.ErrorCode;

public record ErrorResponse(
	String timeStamp,
	String code,
	String message
) {
	public ErrorResponse(String code, String message) {
		this(
			LocalDateTime.now().toString(),
			code,
			message
		);
	}

	public ErrorResponse(ErrorCode errorCode) {
		this(
			LocalDateTime.now().toString(),
			errorCode.getCode(),
			errorCode.getMessage()
		);
	}
}
