package kdt.dev.ecommerce.auth.application.dto;

public record OAuthLoginRequest(
	String code,
	String state
) {
}
