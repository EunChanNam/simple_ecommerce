package kdt.dev.ecommerce.auth.infrastructure.google;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class GoogleProperties {

	private final String clientId;
	private final String clientSecret;
	private final String redirectUri;
	private final Set<String> scope;
	private final String authorizationEndpoint;
	private final String tokenEndpoint;
	private final String userinfoEndpoint;

	public GoogleProperties(
		@Value("${spring.oauth.google.client-id}") String clientId,
		@Value("${spring.oauth.google.client-secret}") String clientSecret,
		@Value("${spring.oauth.google.redirect-uri}") String redirectUri,
		@Value("${spring.oauth.google.scope}") Set<String> scope,
		@Value("${spring.oauth.google.authorization-endpoint}") String authorizationEndpoint,
		@Value("${spring.oauth.google.token-endpoint}") String tokenEndpoint,
		@Value("${spring.oauth.google.userinfo-endpoint}") String userinfoEndpoint
	) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.scope = scope;
		this.authorizationEndpoint = authorizationEndpoint;
		this.tokenEndpoint = tokenEndpoint;
		this.userinfoEndpoint = userinfoEndpoint;
	}
}
