package kdt.dev.ecommerce.global.resolver;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import kdt.dev.ecommerce.auth.domain.AuthTokenManager;
import kdt.dev.ecommerce.auth.exception.AuthError;
import kdt.dev.ecommerce.common.support.MockTestSupport;
import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.global.resolver.dto.LoginInfo;

@DisplayName("[LoginArgumentResolver 테스트]")
class LoginArgumentResolverTest extends MockTestSupport {

	@InjectMocks
	private LoginArgumentResolver loginArgumentResolver;
	@Mock
	private AuthTokenManager authTokenManager;
	@Mock
	private NativeWebRequest webRequest;
	@Mock
	private HttpServletRequest httpServletRequest;

	@BeforeEach
	void setUp() {
		given(webRequest.getNativeRequest(any()))
			.willReturn(httpServletRequest);
	}

	@Nested
	@DisplayName("[로그인한 사용자인지 검증하고 정보를 사용자 정보를 추출한다]")
	class resolveArgument {

		@Test
		@DisplayName("[검증에 성공한다]")
		void success() {
			//given
			given(httpServletRequest.getHeader("Authorization"))
				.willReturn("Bearer token");

			given(authTokenManager.getId("token"))
				.willReturn(1L);

			//when
			Object actual = loginArgumentResolver.resolveArgument(
				Mockito.mock(MethodParameter.class),
				Mockito.mock(ModelAndViewContainer.class),
				webRequest,
				Mockito.mock(WebDataBinderFactory.class)
			);

			//then
			assertThat(actual)
				.isInstanceOf(LoginInfo.class)
				.isEqualTo(new LoginInfo(1L));
		}

		@Test
		@DisplayName("[헤더에 토큰을 포함하지 않아 실패한다]")
		void fail1() {
			//when
			ThrowingCallable when = () -> loginArgumentResolver.resolveArgument(
				Mockito.mock(MethodParameter.class),
				Mockito.mock(ModelAndViewContainer.class),
				webRequest,
				Mockito.mock(WebDataBinderFactory.class)
			);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(AuthError.NEED_LOGIN.getMessage());
		}

		@ParameterizedTest(name = "{0}")
		@ValueSource(strings = {"BearerToken", "Authorization token"})
		@DisplayName("[헤더 형식이 올바르지 않아 실패한다]")
		void fail2(String header) {
			//given
			given(httpServletRequest.getHeader("Authorization"))
				.willReturn(header);

			//when
			ThrowingCallable when = () -> loginArgumentResolver.resolveArgument(
				Mockito.mock(MethodParameter.class),
				Mockito.mock(ModelAndViewContainer.class),
				webRequest,
				Mockito.mock(WebDataBinderFactory.class)
			);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(AuthError.INVALID_AUTHORIZATION_HEADER.getMessage());
		}
	}
}