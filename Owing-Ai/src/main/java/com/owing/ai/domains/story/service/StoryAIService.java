package com.owing.ai.domains.story.service;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.owing.ai.domains.story.dto.request.CrashCheckRequest;
import com.owing.ai.domains.story.dto.request.SystemText;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryAIService {
	private final ChatClient openAiClient;

	public String crashCheck(CrashCheckRequest request) {
			/*
		설정 충돌이란 무엇인가?

		1. 캐릭터 - 성격, 과거(배경, 취향 습관...?, 알레르기), 외모
		2. 세계관 - 역사, 시대적 고증. (의복, 먹는 거, 건물...?
		3. 원고의 이전내용

	 */
		SystemMessage systemMessage = SystemText.template();
		UserMessage userMessage = new UserMessage(request.toString());
		Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

		String res = openAiClient
			.prompt(prompt)
			.system(SystemText.systemText)
			.user(request.toString())
			.call()
			// .entity(CrashCheckResponse.class);
			.content();
		System.out.println(res);
		return res;
	}

}
