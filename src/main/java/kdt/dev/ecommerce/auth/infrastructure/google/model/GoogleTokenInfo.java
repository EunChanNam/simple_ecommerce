package kdt.dev.ecommerce.auth.infrastructure.google.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthTokenInfo;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleTokenInfo(
	String tokenType,
	String accessToken,
	String refreshToken,
	long expiresIn
) implements OAuthTokenInfo {

}
