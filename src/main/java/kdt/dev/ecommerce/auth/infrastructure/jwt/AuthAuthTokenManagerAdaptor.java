package kdt.dev.ecommerce.auth.infrastructure.jwt;

import org.springframework.stereotype.Component;

import kdt.dev.ecommerce.auth.domain.AuthTokenManager;
import kdt.dev.ecommerce.auth.domain.oauth.model.AuthToken;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthAuthTokenManagerAdaptor implements AuthTokenManager {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public AuthToken generate(Long userId) {
		String accessToken = jwtTokenProvider.createAccessToken(userId);
		String refreshToken = jwtTokenProvider.createRefreshToken(userId);
		return new AuthToken(accessToken, refreshToken);
	}

	@Override
	public Long getId(String token) {
		return jwtTokenProvider.getPayload(token);
	}

	@Override
	public void validateToken(String token) {
		jwtTokenProvider.validateToken(token);
	}
}
