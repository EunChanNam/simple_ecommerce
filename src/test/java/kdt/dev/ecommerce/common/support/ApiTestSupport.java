package kdt.dev.ecommerce.common.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kdt.dev.ecommerce.auth.domain.AuthTokenManager;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class ApiTestSupport {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private AuthTokenManager authTokenManager;

	protected String getAccessToken(Long userId) {
		return authTokenManager.generate(userId).accessToken();
	}

	protected String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
