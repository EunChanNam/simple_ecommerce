package kdt.dev.ecommerce.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
		title = "Simple Commerce API",
		description = "Simple Commerce API 명세",
		version = "v1"))
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi chatOpenApi() {
		String[] paths = {"/api/v1/**", "/login/**"};

		return GroupedOpenApi.builder()
			.group("API v1")
			.pathsToMatch(paths)
			.build();
	}
}