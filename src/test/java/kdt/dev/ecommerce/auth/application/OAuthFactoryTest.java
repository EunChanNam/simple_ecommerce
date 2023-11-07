package kdt.dev.ecommerce.auth.application;

import static kdt.dev.ecommerce.auth.exception.AuthError.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import kdt.dev.ecommerce.auth.domain.oauth.OAuthConnector;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthProvider;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthUriGenerator;
import kdt.dev.ecommerce.global.exception.CommerceException;

@DisplayName("[OAuthFactory 테스트]")
class OAuthFactoryTest {

	private final OAuthFactory oAuthFactory;
	private final OAuthConnector oAuthConnector;
	private final OAuthUriGenerator oAuthUriGenerator;

	public OAuthFactoryTest() {
		this.oAuthConnector = Mockito.mock(OAuthConnector.class);
		this.oAuthUriGenerator = Mockito.mock(OAuthUriGenerator.class);
		this.oAuthFactory = new OAuthFactory(
			List.of(oAuthConnector),
			List.of(oAuthUriGenerator)
		);
	}

	@Nested
	@DisplayName("[OAuthConnector 가져온다]")
	class getOAuthConnector {

		@Test
		@DisplayName("[성공적으로 provider 에 일치하는 connector 를 가져온다]")
		void success() {
			//given
			String provider = "google";
			given(oAuthConnector.isSupported(OAuthProvider.GOOGLE)).willReturn(true);

			//when
			OAuthConnector actual = oAuthFactory.getOAuthConnector(provider);

			//then
			assertThat(actual).isEqualTo(oAuthConnector);
		}

		@Test
		@DisplayName("[provider 에 일치하는 connector 가 없어서 실패한다]")
		void fail() {
			//given
			String provider = "google";

			//when
			ThrowingCallable when = () -> oAuthFactory.getOAuthConnector(provider);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(NOT_EXIST_OAUTH_TYPE.getMessage());
		}
	}

	@Nested
	@DisplayName("[OAuthUriGenerator 를 가져온다]")
	class getOAuthUriGenerator {

		@Test
		@DisplayName("[성공적으로 provider 에 일치하는 uriGenerator 를 가져온다]")
		void success() {
			//given
			String provider = "google";
			given(oAuthUriGenerator.isSupported(OAuthProvider.GOOGLE)).willReturn(true);

			//when
			OAuthUriGenerator actual = oAuthFactory.getOAuthUriGenerator(provider);

			//then
			assertThat(actual).isEqualTo(oAuthUriGenerator);
		}

		@Test
		@DisplayName("[provider 에 일치하는 uriGenerator 가 없어서 실패한다]")
		void fail() {
			//given
			String provider = "google";

			//when
			ThrowingCallable when = () -> oAuthFactory.getOAuthUriGenerator(provider);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(NOT_EXIST_OAUTH_TYPE.getMessage());
		}
	}
}