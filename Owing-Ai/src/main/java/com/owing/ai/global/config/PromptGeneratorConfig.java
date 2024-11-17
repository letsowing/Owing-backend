package com.owing.ai.global.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.owing.ai.domains.image.promptGenerator.PromptGenerator;

@Configuration
public class PromptGeneratorConfig {

	public static final String PROMPT_GENERATOR_TYPE = "openAI"; // todo

	@Bean
	public PromptGenerator promptGenerator(
		@Qualifier("openAiPromptGenerator") PromptGenerator promptGenerator) {
		if ("openAI".equalsIgnoreCase(PROMPT_GENERATOR_TYPE)) {
			return promptGenerator;
		} else {
			throw new IllegalArgumentException("지원되지 않는 프롬프트 생성 서비스: " + PROMPT_GENERATOR_TYPE);
		}
	}
}
