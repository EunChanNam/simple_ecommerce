package kdt.dev.ecommerce.auth.domain.oauth;

import static kdt.dev.ecommerce.auth.exception.AuthError.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthTokenInfo;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthUserInfo;
import kdt.dev.ecommerce.auth.infrastructure.google.GoogleConnector;
import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleUserInfo;
import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleTokenInfo;
import kdt.dev.ecommerce.auth.stub.GooglePropertiesStub;
import kdt.dev.ecommerce.global.exception.CommerceException;

@DisplayName("[(구글)OAuthConnector 테스트]")
class OAuthConnectorTest {

	private final OAuthConnector oAuthConnector;
	private final RestTemplate restTemplate;

	public OAuthConnectorTest() {
		restTemplate = Mockito.mock(RestTemplate.class);
		oAuthConnector = new GoogleConnector(restTemplate, new GooglePropertiesStub());
	}

	@Nested
	@DisplayName("[지원하는 OAuth 타입인지 확인한다]")
	class isSupported {

		@Test
		@DisplayName("[지원하는 OAuth 로 true 를 반환한다]")
		void returnTrue() {
			//given
			OAuthProvider provider = OAuthProvider.GOOGLE;

			//when
			boolean actual = oAuthConnector.isSupported(provider);

			//then
			assertThat(actual).isTrue();
		}

		@Test
		@DisplayName("[지원하는 OAuth 로 false 를 반환한다]")
		void returnFalse() {
			//given
			OAuthProvider provider = OAuthProvider.KAKAO;

			//when
			boolean actual = oAuthConnector.isSupported(provider);

			//then
			assertThat(actual).isFalse();
		}
	}

	@Nested
	@DisplayName("[소셜로그인 토큰을 oauth 서버로 부터 받아온다]")
	class fetchToken {

		@Test
		@DisplayName("[성공적으로 받아온다]")
		void success() {
			//given
			GoogleTokenInfo tokenInfo = new GoogleTokenInfo("access-token", "token", "token", 1000);
			given(restTemplate.postForObject(anyString(), any(), any()))
				.willReturn(tokenInfo);

			//when
			OAuthTokenInfo actual = oAuthConnector.fetchToken("code", "state");

			//then
			assertThat(actual).isEqualTo(tokenInfo);
		}

		@Test
		@DisplayName("[oauth 통신간 오류로 실패한다]")
		void fail() {
			//given
			given(restTemplate.postForObject(anyString(), any(), any()))
				.willThrow(new CommerceException(GOOGLE_OAUTH_ERROR));

			//when
			ThrowingCallable when = () -> oAuthConnector.fetchToken("code", "state");

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(GOOGLE_OAUTH_ERROR.getMessage());
		}
	}

	@Nested
	@DisplayName("[사용자 정보를 oauth 서버로 부터 받아온다]")
	class fetchUserInfo {

		@Test
		@DisplayName("[성공적으로 받아온다]")
		void success() {
			//given
			GoogleUserInfo userInfo = new GoogleUserInfo("sub", "name", "email");
			given(restTemplate.exchange(anyString(), any(), any(), eq(GoogleUserInfo.class)))
				.willReturn(new ResponseEntity<>(userInfo, HttpStatus.OK));
			//when
			OAuthUserInfo actual = oAuthConnector.fetchUserInfo("token");

			//then
			assertThat(actual).isEqualTo(userInfo);
		}

		@Test
		@DisplayName("[oauth 통신간 오류로 실패한다]")
		void fail() {
			//given
			given(restTemplate.exchange(anyString(), any(), any(), eq(GoogleUserInfo.class)))
				.willThrow(new CommerceException(GOOGLE_OAUTH_ERROR));

			//when
			ThrowingCallable when = () -> oAuthConnector.fetchUserInfo("token");

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(GOOGLE_OAUTH_ERROR.getMessage());
		}
	}
}