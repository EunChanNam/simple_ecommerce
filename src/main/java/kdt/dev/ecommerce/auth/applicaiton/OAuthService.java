package kdt.dev.ecommerce.auth.applicaiton;

import java.net.URI;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import kdt.dev.ecommerce.auth.applicaiton.dto.AuthTokenResponse;
import kdt.dev.ecommerce.auth.domain.AuthTokenManager;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthConnector;
import kdt.dev.ecommerce.auth.domain.oauth.model.AuthToken;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthTokenInfo;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthUserInfo;
import kdt.dev.ecommerce.user.domain.User;
import kdt.dev.ecommerce.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthService {

	private final OAuthFactory oauthFactory;
	private final AuthTokenManager tokenManager;
	private final UserRepository userRepository;

	private User saveOrUpdateMember(OAuthUserInfo userInfo) {
		Optional<User> optionalUser = userRepository.findByOauthId(userInfo.oauthId());

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.updateFromOAuth(userInfo.nickname());
			return user;
		}

		User newUser = User.of(userInfo.oauthId(), userInfo.nickname());
		return userRepository.save(newUser);
	}

	private URI generateLoginSuccessRedirectUri(final AuthTokenResponse authTokenResponse) {
		String loginSuccessRedirectUri = "http://localhost:8080/view/login-success";
		return UriComponentsBuilder
			.fromUriString(loginSuccessRedirectUri)
			.queryParam("access_token", authTokenResponse.accessToken())
			.queryParam("refresh_token", authTokenResponse.refreshToken())
			.build()
			.toUri();
	}

	public URI getAuthorizationUri(final String provider) {
		return oauthFactory
			.getOAuthUriGenerator(provider)
			.generate();
	}

	@Transactional
	public URI login(
		final String provider,
		final String code,
		final String state
	) {
		OAuthConnector connector = oauthFactory.getOAuthConnector(provider);

		OAuthTokenInfo oauthToken = connector.fetchToken(code, state);
		OAuthUserInfo userInfo = connector.fetchUserInfo(oauthToken.accessToken());

		User user = saveOrUpdateMember(userInfo);

		AuthToken authToken = tokenManager.generate(user.getId());
		AuthTokenResponse authTokenResponse = new AuthTokenResponse(
			authToken.accessToken(),
			authToken.refreshToken()
		);

		return generateLoginSuccessRedirectUri(authTokenResponse);
	}

}