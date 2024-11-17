package com.owing.ai.domains.story.ai.v1.service;

import java.util.List;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.story.ai.AnalyzePromptGenerator;
import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;
import com.owing.ai.domains.story.ai.v1.data.SystemText;

@Service
public class EntirePromptGenerator implements AnalyzePromptGenerator {

	@Override
	public Prompt generatePrompt(CrashCheckRequest request) {
		SystemMessage systemMessage = SystemText.template();
		UserMessage userMessage = new UserMessage(request.toString());
		return new Prompt(List.of(systemMessage, userMessage));
	}
}
