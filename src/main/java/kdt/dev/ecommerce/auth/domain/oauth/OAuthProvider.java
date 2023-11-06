package kdt.dev.ecommerce.auth.domain.oauth;

import java.util.Arrays;

import kdt.dev.ecommerce.auth.exception.AuthError;
import kdt.dev.ecommerce.global.exception.CommerceException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {

	GOOGLE("google"),
	KAKAO("kakao");

	private final String provider;

	public static OAuthProvider from(String provider) {
		return Arrays.stream(values())
			.filter(oauthType -> oauthType.getProvider().equals(provider))
			.findFirst()
			.orElseThrow(() -> new CommerceException(AuthError.NOT_EXIST_OAUTH_TYPE));
	}

	public boolean isGoogle() {
		return this.equals(GOOGLE);
	}

	public boolean isKakao() {
		return this.equals(KAKAO);
	}
}
