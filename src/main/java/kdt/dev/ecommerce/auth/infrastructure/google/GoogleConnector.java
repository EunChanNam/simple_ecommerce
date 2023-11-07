package kdt.dev.ecommerce.auth.infrastructure.google;

import static kdt.dev.ecommerce.auth.exception.AuthError.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import kdt.dev.ecommerce.auth.domain.oauth.OAuthConnector;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthProvider;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthUserInfo;
import kdt.dev.ecommerce.auth.domain.oauth.model.OAuthTokenInfo;
import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleUserInfo;
import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleTokenInfo;
import kdt.dev.ecommerce.global.exception.CommerceException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleConnector implements OAuthConnector {

	private static final String GRANT_TYPE = "authorization_code";
	private static final String BEARER_TYPE = "Bearer ";

	private final RestTemplate restTemplate;
	private final GoogleProperties properties;

	private HttpHeaders createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(APPLICATION_FORM_URLENCODED);
		return headers;
	}

	@Override
	public boolean isSupported(OAuthProvider provider) {
		return provider.isGoogle();
	}

	@Override
	public OAuthTokenInfo fetchToken(String code, String state) {
		HttpHeaders headers = createHttpHeaders();

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", GRANT_TYPE);
		body.add("client_id", properties.getClientId());
		body.add("redirect_uri", properties.getRedirectUri());
		body.add("client_secret", properties.getClientSecret());
		body.add("state", state);
		body.add("code", code);

		HttpEntity<?> request = new HttpEntity<>(body, headers);

		try {
			return restTemplate.postForObject(
				properties.getTokenEndpoint(),
				request,
				GoogleTokenInfo.class
			);
		} catch (RestClientException e) {
			throw new CommerceException(GOOGLE_OAUTH_ERROR);
		}
	}

	@Override
	public OAuthUserInfo fetchUserInfo(String accessToken) {
		HttpHeaders headers = createHttpHeaders();
		headers.set("Authorization", BEARER_TYPE + accessToken);

		HttpEntity<?> request = new HttpEntity<>(headers);

		try {
			return restTemplate.exchange(
				properties.getUserinfoEndpoint(),
				GET,
				request,
				GoogleUserInfo.class
			).getBody();
		} catch (RestClientException e) {
			throw new CommerceException(GOOGLE_OAUTH_ERROR);
		}
	}
}
