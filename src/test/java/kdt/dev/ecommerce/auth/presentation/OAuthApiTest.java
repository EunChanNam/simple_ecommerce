package kdt.dev.ecommerce.auth.presentation;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleTokenInfo;
import kdt.dev.ecommerce.auth.infrastructure.google.model.GoogleUserInfo;
import kdt.dev.ecommerce.common.support.ApiTestSupport;

@DisplayName("[OAuth API 테스트]")
class OAuthApiTest extends ApiTestSupport {

	private static final String REDIRECT_TO_OAUTH_PAGE_URL = "/api/v1/oauth/%s";
	private static final String LOGIN_URL = "/login/oauth2/code/%s";

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private RestTemplate restTemplate;

	@BeforeEach
	void setUp() {
		GoogleTokenInfo tokenInfo = new GoogleTokenInfo("access-token", "token", "token", 1000);
		given(restTemplate.postForObject(anyString(), any(), any()))
			.willReturn(tokenInfo);

		GoogleUserInfo userInfo = new GoogleUserInfo("sub", "name", "email");
		given(restTemplate.exchange(anyString(), any(), any(), eq(GoogleUserInfo.class)))
			.willReturn(new ResponseEntity<>(userInfo, HttpStatus.OK));
	}

	@Test
	void redirectToOAuthPage() throws Exception {
		//given
		String provider = "google";

		//when
		ResultActions actual = mockMvc.perform(
			MockMvcRequestBuilders
				.get(String.format(REDIRECT_TO_OAUTH_PAGE_URL, provider))
		);

		//then
		actual.andExpect(status().isFound());
	}

	@Test
	void login() throws Exception {
		//given
		String provider = "google";

		String requestUrl = UriComponentsBuilder
			.fromUriString(String.format(LOGIN_URL, provider))
			.queryParam("state", "state")
			.queryParam("code", "code")
			.build().toString();

		//when
		ResultActions actual = mockMvc.perform(
			MockMvcRequestBuilders
				.get(requestUrl)
		);

		//then
		actual.andExpect(status().isFound());
	}
}