package com.owing.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

	@Bean
	@Qualifier("openAiClient")
	public ChatClient openAiClient(OpenAiChatModel openAiChatModel) {
		return ChatClient.builder(openAiChatModel).build();
	}

}
