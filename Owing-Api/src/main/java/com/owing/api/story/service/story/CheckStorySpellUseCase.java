package com.owing.api.story.service.story;

import java.util.List;

import com.owing.api.openfeign.OwingAiClient;
import com.owing.api.story.model.dto.request.StorySpellCheckRequest;
import com.owing.api.story.model.dto.response.StorySpellCheckResponse;
import com.owing.api.story.model.mapper.StoryAiLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.model.StoryAiLog;
import com.owing.entity.domains.ai.log.story.service.StoryAiLogDomainService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.service.StoryDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CheckStorySpellUseCase {

	private final StoryDomainService storyDomainService;

	private final OwingAiClient owingAiClient;
	private final StoryAdapter storyAdapter;
	private final StoryAiLogDomainService storyAiLogDomainService;
	private final StoryAiLogMapper storyAiLogMapper;

	public List<StorySpellCheckResponse> execute(Long storyId) {
		Story story = storyAdapter.findById(storyId);
		String content = storyDomainService.getParsedContent(story);

		if(content.isBlank()){
			return List.of();
		}

		StorySpellCheckRequest request = StorySpellCheckRequest.of(content);

		List<StorySpellCheckResponse> storySpellCheckResponses = owingAiClient.spellCheck(request);
		logging(story, storySpellCheckResponses);

		return storySpellCheckResponses;
	}

	private void logging(Story story, List<StorySpellCheckResponse> aiResponse) {
		StoryAiLog aiLogEntity = storyAiLogMapper.toEntity(story, aiResponse);
		storyAiLogDomainService.createLog(aiLogEntity);
	}
}
