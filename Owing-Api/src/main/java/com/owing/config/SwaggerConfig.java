package com.owing.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 설정 클래스
 * Swagger 를 사용하여 API 명세서를 자동으로 생성하며, API 보안 설정을 정의
 */
@OpenAPIDefinition(
	info = @Info(
		title = "Owing api 명세서",
		description = """
			<h3>Owing API Reference for Developers</h3>
			""",
		version = "v1",
		contact = @Contact(
			name = "Owing",
			email = "letsowing@gmail.com",
			url = ""
		)
	)
)
@Configuration
public class SwaggerConfig {

	private static final String SECURITY_SCHEME_NAME = "JWT";

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
			.components(new io.swagger.v3.oas.models.Components()
				.addSecuritySchemes(SECURITY_SCHEME_NAME, createJwtSecurityScheme()));
	}

	private SecurityScheme createJwtSecurityScheme() {
		return new SecurityScheme()
			.name(SECURITY_SCHEME_NAME)
			.type(Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.in(In.HEADER)
			.description("JWT Bearer Token을 사용한 인증. Bearer 키에 토큰을 입력하세요.");
	}
}
