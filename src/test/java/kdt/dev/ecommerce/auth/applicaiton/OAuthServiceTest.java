package kdt.dev.ecommerce.auth.applicaiton;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import kdt.dev.ecommerce.auth.domain.AuthTokenManager;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthConnector;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthUriGenerator;
import kdt.dev.ecommerce.auth.domain.oauth.model.AuthToken;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthTokenInfo;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthUserInfo;
import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleTokenInfo;
import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleUserInfo;
import kdt.dev.ecommerce.user.domain.User;
import kdt.dev.ecommerce.user.domain.UserRepository;
import kdt.dev.ecommerce.user.fixture.UserFixture;

@DisplayName("[OAuthService 테스트]")
@ExtendWith(MockitoExtension.class)
class OAuthServiceTest {

	@InjectMocks
	private OAuthService oAuthService;

	@Mock
	private OAuthFactory oAuthFactory;
	@Mock
	private AuthTokenManager tokenManager;
	@Mock
	private UserRepository userRepository;

	private OAuthUserInfo mockingForLogin(String provider, String code, String state) {
		OAuthConnector oAuthConnector = Mockito.mock(OAuthConnector.class);
		given(oAuthFactory.getOAuthConnector(provider))
			.willReturn(oAuthConnector);

		OAuthTokenInfo tokenInfo = new GoogleTokenInfo("type", "access-token", "refresh-token", 1000);
		given(oAuthConnector.fetchToken(code, state)).willReturn(tokenInfo);

		OAuthUserInfo userInfo = new GoogleUserInfo("sub", "helloName", "email");
		given(oAuthConnector.fetchUserInfo(tokenInfo.accessToken())).willReturn(userInfo);

		given(tokenManager.generate(1L)).willReturn(new AuthToken("at", "rt"));
		return userInfo;
	}

	@Test
	@DisplayName("[소셜로그인 URI 를 가져온다]")
	void getAuthorizationUri() {
		//given
		String provider = "google";

		OAuthUriGenerator oAuthUriGenerator = Mockito.mock(OAuthUriGenerator.class);
		given(oAuthFactory.getOAuthUriGenerator(provider))
			.willReturn(oAuthUriGenerator);

		String uri = "http:/hello/";
		given(oAuthUriGenerator.generate())
			.willReturn(URI.create(uri));

		//when
		URI actual = oAuthService.getAuthorizationUri(provider);

		//then
		assertThat(actual).hasToString(uri);
	}

	@Nested
	@DisplayName("[소셜로그인을 한다]")
	class login {

		@Test
		@DisplayName("[기존에 존재하는 회원으로 최신 정보로 회원정보를 업데이트한다]")
		void success1() {
			//given
			String provider = "google";
			String code = "code";
			String state = "state";

			OAuthUserInfo userInfo = mockingForLogin(provider, code, state);

			User user = UserFixture.getUser(1L);
			given(userRepository.findByOauthId(userInfo.oauthId()))
				.willReturn(Optional.of(user));

			//when
			URI actual = oAuthService.login(provider, code, state);

			//then
			assertThat(actual).isNotNull();
			assertThat(user.getNickname()).isEqualTo(userInfo.nickname());
		}

		@Test
		@DisplayName("[기존에 존재하는 회원으로 최신 정보로 회원정보를 업데이트한다]")
		void success2() {
			//given
			String provider = "google";
			String code = "code";
			String state = "state";

			OAuthUserInfo userInfo = mockingForLogin(provider, code, state);

			given(userRepository.findByOauthId(userInfo.oauthId()))
				.willReturn(Optional.empty());

			User user = UserFixture.getUser(1L);
			given(userRepository.save(any(User.class))).willReturn(user);

			//when
			URI actual = oAuthService.login(provider, code, state);

			//then
			assertThat(actual).isNotNull();
			verify(userRepository, times(1)).save(any(User.class));
		}
	}
}