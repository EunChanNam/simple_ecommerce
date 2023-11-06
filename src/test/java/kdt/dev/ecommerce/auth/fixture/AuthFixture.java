package kdt.dev.ecommerce.auth.fixture;

import kdt.dev.ecommerce.auth.application.dto.OAuthLoginRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthFixture {

	public static OAuthLoginRequest getOAuthLoginRequest() {
		return new OAuthLoginRequest("code", "state");
	}
}
