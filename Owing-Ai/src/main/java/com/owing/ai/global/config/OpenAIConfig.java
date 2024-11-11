package com.owing.ai.global.config;

import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.owing.ai.global.properties.OpenAiImageProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenAIConfig {

	private final OpenAiImageProperties openAiImageProperties;

	/**
	 * OpenAI 이미지 API 를 위한 Bean 을 생성하는 메서드
	 *
	 * @return OpenAiImageApi 객체
	 */
	@Bean
	public OpenAiImageApi openAiImageApi() {
		return new OpenAiImageApi(openAiImageProperties.apiKey());
	}
}
