package kdt.dev.ecommerce.auth.presentation;

import static org.springframework.http.HttpStatus.*;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kdt.dev.ecommerce.auth.application.OAuthService;
import kdt.dev.ecommerce.auth.application.dto.OAuthLoginRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuthApiController {

	private final OAuthService oauthService;

	@Operation(summary = "소셜로그인 요청 API", description = "호출 시 소셜로그인 페이지로 리다이렉트됨", tags = {"OAuth API"})
	@ApiResponse(responseCode = "302", description = "리다이렉트 성공")
	@GetMapping("/api/v1/oauth/oauth/{provider}")
	public ResponseEntity<Void> redirectToOAuthPage(
		@PathVariable String provider
	) {
		URI authorizationUri = oauthService.getAuthorizationUri(provider);

		HttpHeaders headers = createRedirectHeaders(authorizationUri);

		return new ResponseEntity<>(headers, FOUND);
	}

	@Operation(
		summary = "소셜로그인 처리 API",
		description = "OAuth 서버와 통신하며 인증처리, 소셜로그인 요청 API 호출 시 자동 호출됨",
		tags = {"OAuth API"}
	)
	@ApiResponse(
		responseCode = "302",
		description = "소셜로그인 성공 후 성공 url 로 자동으로 리다이랙트",
		headers = @Header(name = "Location", description = "로그인 성공 페이지 URL")
	)
	@GetMapping("/login/oauth2/code/{provider}")
	public ResponseEntity<Void> login(
		@PathVariable String provider,
		@ModelAttribute OAuthLoginRequest request
	) {
		URI redirectUri = oauthService.login(provider, request);

		HttpHeaders headers = createRedirectHeaders(redirectUri);

		return new ResponseEntity<>(headers, FOUND);
	}

	private HttpHeaders createRedirectHeaders(URI redirectUri) {
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(redirectUri);
		return headers;
	}
}
