package kdt.dev.ecommerce.auth.domain.oauth;

import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthUserInfo;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthTokenInfo;

public interface OAuthConnector {

	boolean isSupported(OAuthProvider provider);

	OAuthTokenInfo fetchToken(String code, String state);

	OAuthUserInfo fetchUserInfo(String accessToken);
}
