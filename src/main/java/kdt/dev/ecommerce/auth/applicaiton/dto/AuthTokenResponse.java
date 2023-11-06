package kdt.dev.ecommerce.auth.applicaiton.dto;

public record AuthTokenResponse(
	String accessToken,
	String refreshToken
) {
}
