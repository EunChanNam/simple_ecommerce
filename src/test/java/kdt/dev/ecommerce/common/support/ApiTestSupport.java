package kdt.dev.ecommerce.common.support;

import static kdt.dev.ecommerce.common.fixture.AuthFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kdt.dev.ecommerce.auth.domain.AuthTokenManager;
import kdt.dev.ecommerce.auth.domain.oauth.model.AuthToken;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class ApiTestSupport {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@MockBean
	private AuthTokenManager authTokenManager;

	protected void setAuthorization(Long userId) {
		given(authTokenManager.generate(any(Long.class))).willReturn(new AuthToken(TOKEN, TOKEN));
		given(authTokenManager.getId(TOKEN)).willReturn(userId);
	}

	protected String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
