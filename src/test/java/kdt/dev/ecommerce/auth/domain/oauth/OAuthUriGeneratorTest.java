package kdt.dev.ecommerce.auth.domain.oauth;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kdt.dev.ecommerce.auth.infrastructure.google.GoogleUriGenerator;
import kdt.dev.ecommerce.auth.stub.GooglePropertiesStub;

@DisplayName("[(구글)OAuthUriGenerator 테스트]")
class OAuthUriGeneratorTest {

	private final OAuthUriGenerator uriGenerator;

	public OAuthUriGeneratorTest() {
		uriGenerator = new GoogleUriGenerator(new GooglePropertiesStub());
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
			boolean actual = uriGenerator.isSupported(provider);

			//then
			assertThat(actual).isTrue();
		}

		@Test
		@DisplayName("[지원하는 OAuth 로 false 를 반환한다]")
		void returnFalse() {
			//given
			OAuthProvider provider = OAuthProvider.KAKAO;

			//when
			boolean actual = uriGenerator.isSupported(provider);

			//then
			assertThat(actual).isFalse();
		}
	}

	@Test
	@DisplayName("[OAuth 접속 uri 를 생성한다]")
	void generate() {
		//when
		URI actual = uriGenerator.generate();

		//then
		assertThat(actual).isNotNull();
	}
}