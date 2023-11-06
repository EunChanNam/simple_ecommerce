package kdt.dev.ecommerce.global.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	String getCode();

	HttpStatus getStatus();

	String getMessage();
}
