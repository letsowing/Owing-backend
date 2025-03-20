package com.owing.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.oidc")
public record GoogleOauthProperties(
        String audience
) {
}
