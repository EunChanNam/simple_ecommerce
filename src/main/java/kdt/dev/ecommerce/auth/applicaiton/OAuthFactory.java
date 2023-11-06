package kdt.dev.ecommerce.auth.applicaiton;

import static kdt.dev.ecommerce.auth.exception.AuthError.*;

import java.util.List;

import org.springframework.stereotype.Component;

import kdt.dev.ecommerce.auth.domain.oauth.OAuthConnector;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthProvider;
import kdt.dev.ecommerce.auth.domain.oauth.OAuthUriGenerator;
import kdt.dev.ecommerce.global.exception.CommerceException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthFactory {

	private final List<OAuthConnector> connectors;
	private final List<OAuthUriGenerator> uriProviders;

	public OAuthConnector getOAuthConnector(final String provider) {
		OAuthProvider authProvider = OAuthProvider.from(provider);

		return connectors.stream()
			.filter(connector -> connector.isSupported(authProvider))
			.findFirst()
			.orElseThrow(() -> new CommerceException(NOT_EXIST_OAUTH_TYPE));
	}

	public OAuthUriGenerator getOAuthUriGenerator(final String provider) {
		OAuthProvider authProvider = OAuthProvider.from(provider);

		return uriProviders.stream()
			.filter(uriProvider -> uriProvider.isSupported(authProvider))
			.findFirst()
			.orElseThrow(() -> new CommerceException(NOT_EXIST_OAUTH_TYPE));
	}
}
