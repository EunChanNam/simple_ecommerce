package kdt.dev.ecommerce.auth.domain;

import kdt.dev.ecommerce.auth.domain.oauth.model.AuthToken;

public interface AuthTokenManager {

	AuthToken generate(Long userId);

	Long getId(String token);

	void validateToken(String token);
}
