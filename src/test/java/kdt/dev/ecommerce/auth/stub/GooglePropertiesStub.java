package kdt.dev.ecommerce.auth.stub;

import java.util.Set;

import kdt.dev.ecommerce.auth.infrastructure.google.GoogleProperties;

public class GooglePropertiesStub extends GoogleProperties {

	public GooglePropertiesStub() {
		super(null, null, null, null, null, null, null);
	}

	@Override
	public String getUserinfoEndpoint() {
		return "user-info-end-point";
	}

	@Override
	public String getTokenEndpoint() {
		return "token-end-point";
	}

	@Override
	public String getAuthorizationEndpoint() {
		return "auth-end-point";
	}

	@Override
	public Set<String> getScope() {
		return Set.of("scope");
	}

	@Override
	public String getRedirectUri() {
		return "redirect-uri";
	}

	@Override
	public String getClientSecret() {
		return "secret";
	}

	@Override
	public String getClientId() {
		return "client-id";
	}
}
