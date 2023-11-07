package kdt.dev.ecommerce.auth.infrastructure.google;

import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import kdt.dev.ecommerce.auth.domain.oauth.OAuthProvider;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthUriGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleUriGenerator implements OAuthUriGenerator {

	private final GoogleProperties properties;

	@Override
	public boolean isSupported(OAuthProvider provider) {
		return provider.isGoogle();
	}

	@Override
	public URI generate() {
		return UriComponentsBuilder
			.fromUriString(properties.getAuthorizationEndpoint())
			.queryParam("response_type", "code")
			.queryParam("client_id", properties.getClientId())
			.queryParam("scope", String.join(" ", properties.getScope()))
			.queryParam("redirect_uri", properties.getRedirectUri())
			.queryParam("state", UUID.randomUUID().toString().replaceAll("-", ""))
			.build()
			.toUri();
	}
}
