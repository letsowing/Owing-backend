package com.owing.ai.global.properties;

import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.ai.openai")
public record OpenAiProperties(
	String apiKey,
	Image image,
	Chat chat
) {
	public record Image (
		ImageOptions options
	) {}
	public record ImageOptions (
		OpenAiImageApi.ImageModel model,
		String responseFormat,
		String quality,
		String size
	) {}
	public record Chat (
		ChatOptions options
	) {}
	public record ChatOptions (
		String model,
		// String responseFormat,
		String temperature,
		String maxTokens
	) {}
}
