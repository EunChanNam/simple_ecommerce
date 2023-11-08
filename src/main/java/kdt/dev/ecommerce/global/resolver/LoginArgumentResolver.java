package kdt.dev.ecommerce.global.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import kdt.dev.ecommerce.auth.domain.AuthTokenManager;
import kdt.dev.ecommerce.auth.exception.AuthError;
import kdt.dev.ecommerce.global.annotation.Login;
import kdt.dev.ecommerce.global.exception.CommerceException;
import kdt.dev.ecommerce.global.resolver.dto.LoginInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER = "Bearer";

	private final AuthTokenManager authTokenManager;

	private void validateHasAuthorizationHeader(String header) {
		if (!StringUtils.hasText(header)) {
			throw new CommerceException(AuthError.NEED_LOGIN);
		}
	}

	private void validateAuthorizationHeader(String header) {
		String[] splitHeader = header.split(" ");

		if (splitHeader.length != 2 || !splitHeader[0].equals(BEARER)) {
			throw new CommerceException(AuthError.INVALID_AUTHORIZATION_HEADER);
		}

		authTokenManager.validateToken(splitHeader[1]);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(Login.class);
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		assert request != null;

		String header = request.getHeader(AUTHORIZATION);
		validateHasAuthorizationHeader(header);
		validateAuthorizationHeader(header);

		String token = header.split(" ")[1];

		return new LoginInfo(authTokenManager.getId(token));
	}
}
