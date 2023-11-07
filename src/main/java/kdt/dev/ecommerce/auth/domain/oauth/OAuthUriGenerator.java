package kdt.dev.ecommerce.auth.domain.oauth;

import java.net.URI;

public interface OAuthUriGenerator {

	boolean isSupported(OAuthProvider provider);

	URI generate();
}
