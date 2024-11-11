package com.owing.ai.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.ai.fal")
public record FalAiImageProperties(
	String apiKey,
	String model
) {
}
