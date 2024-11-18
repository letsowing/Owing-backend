package com.owing.api.story.service.story;

import java.util.List;

import com.owing.api.openfeign.OwingAiClient;
import com.owing.api.story.model.dto.request.StorySpellCheckRequest;
import com.owing.api.story.model.dto.response.StorySpellCheckLogResponse;
import com.owing.api.story.model.dto.response.StorySpellCheckResponse;
import com.owing.api.story.model.mapper.SpellCheckLogMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.ai.log.story.model.SpellCheckLog;
import com.owing.entity.domains.ai.log.story.service.SpellCheckLogDomainService;
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

	// logging
	private final SpellCheckLogDomainService spellCheckLogDomainService;
	private final SpellCheckLogMapper spellCheckLogMapper;

	public StorySpellCheckLogResponse execute(Long storyId) {
		Story story = storyAdapter.findById(storyId);
		String content = storyDomainService.getParsedContent(story);

		if(content.isBlank()){
			return StorySpellCheckLogResponse.nullContent();
		}

		StorySpellCheckRequest request = StorySpellCheckRequest.of(content);

		List<StorySpellCheckResponse> storySpellCheckResponses = owingAiClient.spellCheck(request);
		return logging(story, storySpellCheckResponses);
	}

	private StorySpellCheckLogResponse logging(Story story, List<StorySpellCheckResponse> aiResponse) {
		SpellCheckLog aiLogEntity = spellCheckLogMapper.toEntity(story, aiResponse);
		SpellCheckLog savedLog = spellCheckLogDomainService.createLog(aiLogEntity);
		return spellCheckLogMapper.toLogResponse(savedLog);
	}

}
