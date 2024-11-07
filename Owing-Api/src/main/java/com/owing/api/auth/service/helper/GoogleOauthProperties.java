package com.owing.api.auth.service.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.oidc")
public record GoogleOauthProperties(
        String audience
) {
}
