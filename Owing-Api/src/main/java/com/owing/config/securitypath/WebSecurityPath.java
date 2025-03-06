package com.owing.config.securitypath;

import lombok.Getter;

@Getter
public enum WebSecurityPath {
	REQUIRE_AUTH_PATH("/v1/members/**", "/v1/projects/**", "/v1/stories/**", "/v1/cast/**", "/v1/universes/**", "/v1/trashcans/**", "/v1/dashboard"),
	SWAGGER_PATH("/v1/auth/**", "swagger-ui/index.html", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**", "/api-docs/**", "/api-docs"),
	;

	private final String[] paths;

	WebSecurityPath(String... paths) {
		this.paths = paths;
	}
}
