package com.owing.ai.global.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.owing.ai.global.properties.OpenAiProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OpenAIConfig {

	private final OpenAiProperties openAiProperties;

	/**
	 * OpenAI 이미지 API 를 위한 Bean 을 생성하는 메서드
	 *
	 * @return OpenAiImageApi 객체
	 */
	@Bean
	public OpenAiImageApi openAiImageApi() {
		return new OpenAiImageApi(openAiProperties.apiKey());
	}

	@Bean
	@Qualifier("openAiClient")
	public ChatClient openAiClient(OpenAiChatModel openAiChatModel) {
		return ChatClient.builder(openAiChatModel).build();
	}
}
