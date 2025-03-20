package com.owing.config.securitypath;

import lombok.Getter;

@Getter
public enum WebSecurityPath {
	REQUIRE_AUTH_PATH("/v1/members/**", "/v1/projects/**", "/v1/stories/**", "/v1/cast/**", "/v1/universes/**", "/v1/trashcans/**", "/v1/dashboard");

	private final String[] paths;

	WebSecurityPath(String... paths) {
		this.paths = paths;
	}
}
