package com.owing.ai.domains.story.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owing.ai.domains.story.ai.v1.service.EntirePromptGenerator;
import com.owing.ai.domains.story.ai.v2.model.entity.CrashCheckHistory;
import com.owing.ai.domains.story.ai.v2.model.repository.CrashCheckHistoryRepository;
import com.owing.ai.domains.story.ai.v2.service.RagPromptGenerator;
import com.owing.ai.domains.story.dto.request.crashCheck.CrashCheckRequest;
import com.owing.ai.domains.story.dto.response.crashCheck.CrashCheckResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryAIService {
	private final ChatClient openAiClient;
	private final EntirePromptGenerator entirePromptGenerator;
	private final RagPromptGenerator ragPromptGenerator;
	private final ObjectMapper objectMapper;
	private final CrashCheckHistoryRepository crashCheckHistoryRepository;

	public CrashCheckResponse crashCheck(CrashCheckRequest request) {
		LocalDateTime start = LocalDateTime.now();

		Prompt prompt = ragPromptGenerator.generatePrompt(request);
		LocalDateTime end = LocalDateTime.now();

		CrashCheckResponse res = openAiClient
			.prompt(prompt)
			.call()
			.entity(CrashCheckResponse.class);

		LocalDateTime end2 = LocalDateTime.now();
		String log = logToString(start, end, end2, prompt, res);
		insertLog(request.project().id(), request.thisEpisode().id(), log);

		return res;
	}

	public String logToString(LocalDateTime start, LocalDateTime end, LocalDateTime end2,
		Prompt prompt, CrashCheckResponse response) {
		try {
			return objectMapper.writeValueAsString(Map.of("requestedAt", start,
				"prompt generated At", end,
				"openai client responsedAt", end2,
				"prompt", prompt.getContents(),
				"response", response));
		} catch (Exception e) {
			e.printStackTrace();
			return "Error occurred while converting log to string.";
		}
	}

	public void insertLog(Long projectId, Long storyId, String log) {
		CrashCheckHistory history = CrashCheckHistory.builder()
			.createdAt(LocalDateTime.now())
			.projectId(projectId)
			.storyId(storyId)
			.log(log)
			.build();
		crashCheckHistoryRepository.save(history);
	}

}
