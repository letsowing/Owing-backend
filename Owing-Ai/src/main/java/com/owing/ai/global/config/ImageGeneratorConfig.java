package com.owing.ai.global.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.owing.ai.domains.image.strategy.ImageGenerator;

@Configuration
public class ImageGeneratorConfig {

	public static final String IMAGE_GENERATOR_TYPE = "falAI"; // todo

	@Bean
	public ImageGenerator imageGenerator(
		@Qualifier("openAiImageGenerator") ImageGenerator openAIImageGenerator,
		@Qualifier("falAiImageGenerator") ImageGenerator falAIImageGenerator) {

		if ("openAI".equalsIgnoreCase(IMAGE_GENERATOR_TYPE)) {
			return openAIImageGenerator;
		} else if ("falAI".equalsIgnoreCase(IMAGE_GENERATOR_TYPE)) {
			return falAIImageGenerator;
		} else {
			throw new IllegalArgumentException("지원되지 않는 이미지 생성 서비스: " + IMAGE_GENERATOR_TYPE);
		}
	}
}
