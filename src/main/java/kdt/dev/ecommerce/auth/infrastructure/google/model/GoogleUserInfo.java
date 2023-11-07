package kdt.dev.ecommerce.auth.infrastructure.google.model;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.*;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthUserInfo;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleUserInfo(
	String sub,
	String name,
	String email
) implements OAuthUserInfo {

	@Override
	public String oauthId() {
		return sub;
	}

	@Override
	public String nickname() {
		return name;
	}
}
