package com.owing.ai.global.properties;

import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.ai.openai")
public record OpenAiImageProperties (
	String apiKey,
	Image image
) {
	public record Image (
		Options options
	) {}
	public record Options (
		OpenAiImageApi.ImageModel model,
		String responseFormat,
		String quality,
		String size
	) {}
}
