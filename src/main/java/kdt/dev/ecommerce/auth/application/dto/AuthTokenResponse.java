package kdt.dev.ecommerce.auth.application.dto;

public record AuthTokenResponse(
	String accessToken,
	String refreshToken
) {
}
