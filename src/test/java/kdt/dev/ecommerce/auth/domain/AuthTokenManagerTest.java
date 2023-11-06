package kdt.dev.ecommerce.auth.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import kdt.dev.ecommerce.auth.domain.oauth.model.AuthToken;
import kdt.dev.ecommerce.auth.exception.AuthError;
import kdt.dev.ecommerce.auth.infrastructure.jwt.AuthAuthTokenManagerAdaptor;
import kdt.dev.ecommerce.auth.infrastructure.jwt.JwtTokenProvider;
import kdt.dev.ecommerce.global.exception.CommerceException;

@DisplayName("[AuthTokenManager 테스트]")
class AuthTokenManagerTest {

	private final AuthTokenManager authTokenManager;
	private final String secretKey;
	private final String differentSecretKey;

	public AuthTokenManagerTest() {
		secretKey = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		differentSecretKey = secretKey.replaceAll("a", "b");
		JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(secretKey, 10000, 10000);
		authTokenManager = new AuthAuthTokenManagerAdaptor(jwtTokenProvider);
	}

	private void assertThrownByExpiredToken(ThrowingCallable when) {
		assertThatThrownBy(when)
			.isInstanceOf(CommerceException.class)
			.hasMessageContaining(AuthError.TOKEN_EXPIRED.getMessage());
	}

	private void assertThrownByInvalidToken(ThrowingCallable... whens) {
		for (ThrowingCallable when : whens) {
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(AuthError.TOKEN_INVALID.getMessage());
		}
	}

	@Test
	@DisplayName("[인증 토큰을 발행한다]")
	void generate() {
		//when
		AuthToken actual = authTokenManager.generate(1L);

		//then
		assertDoesNotThrow(() -> authTokenManager.validateToken(actual.accessToken()));
		assertDoesNotThrow(() -> authTokenManager.validateToken(actual.refreshToken()));
	}

	@Nested
	@DisplayName("[토큰에서 id 를 추출한다]")
	class getId {

		@Test
		@DisplayName("[성공적으로 id 를 추출한다]")
		void success() {
			long userId = 1L;
			AuthToken authToken = authTokenManager.generate(userId);
			String token = authToken.accessToken();

			//when
			Long actual = authTokenManager.getId(token);

			//then
			assertThat(actual).isEqualTo(userId);
		}

		@Test
		@DisplayName("[유효하지 않은 토큰으로 추출에 실패한다]")
		void failByInvalidToken() {
			//given
			AuthTokenManager differentKeyTokenManager = new AuthAuthTokenManagerAdaptor(
				new JwtTokenProvider(differentSecretKey, 100, 100));

			String token1 = authTokenManager.generate(1L).accessToken() + "e";
			String token2 = differentKeyTokenManager.generate(1L).accessToken();

			//when
			ThrowingCallable when1 = () -> authTokenManager.getId(token1);
			ThrowingCallable when2 = () -> authTokenManager.getId(token2);

			//then
			assertThrownByInvalidToken(when1, when2);
		}

		@Test
		@DisplayName("[유효기간이 만료된 토큰으로 추출에 실패한다]")
		void failByExpired() {
			//given
			AuthTokenManager timeOutTokenManger = new AuthAuthTokenManagerAdaptor(
				new JwtTokenProvider(secretKey, 0, 0));

			String token = timeOutTokenManger.generate(1L).accessToken();

			//when
			ThrowingCallable when = () -> authTokenManager.getId(token);

			//then
			assertThrownByExpiredToken(when);
		}
	}

	@Nested
	@DisplayName("[토큰을 검증한다]")
	class validateToken {

		@Test
		@DisplayName("[검증에 성공한다]")
		void success() {
			//given
			String token = authTokenManager.generate(1L).accessToken();

			//when
			Executable when = () -> authTokenManager.validateToken(token);

			//then
			assertDoesNotThrow(when);
		}

		@Test
		@DisplayName("[유효하지 않은 토큰으로 검증에 실패한다]")
		void failByInvalidToken() {
			//given
			AuthTokenManager differentKeyTokenManager = new AuthAuthTokenManagerAdaptor(
				new JwtTokenProvider(differentSecretKey, 100, 100)
			);

			String token1 = authTokenManager.generate(1L).accessToken() + "e";
			String token2 = differentKeyTokenManager.generate(1L).accessToken();

			//when
			ThrowingCallable when1 = () -> authTokenManager.validateToken(token1);
			ThrowingCallable when2 = () -> authTokenManager.validateToken(token2);

			//then
			assertThrownByInvalidToken(when1, when2);
		}

		@Test
		@DisplayName("[유효기간이 만료된 토큰으로 검증에 실패한다]")
		void failByExpired() {
			//given
			AuthTokenManager timeOutTokenManger = new AuthAuthTokenManagerAdaptor(
				new JwtTokenProvider(secretKey, 0, 0));

			String token = timeOutTokenManger.generate(1L).accessToken();

			//when
			ThrowingCallable when = () -> authTokenManager.getId(token);

			//then
			assertThrownByExpiredToken(when);
		}
	}
}