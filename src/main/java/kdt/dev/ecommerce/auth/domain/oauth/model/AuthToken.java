package kdt.dev.ecommerce.auth.domain.oauth.model;

public record AuthToken(
	String accessToken,
	String refreshToken
) {
}
