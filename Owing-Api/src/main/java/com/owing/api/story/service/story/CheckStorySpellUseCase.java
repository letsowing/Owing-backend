package com.owing.api.story.service.story;

import java.util.List;

import com.owing.api.openfeign.OwingAiClient;
import com.owing.api.story.model.dto.request.StorySpellCheckRequest;
import com.owing.api.story.model.dto.response.StorySpellCheckResponse;
import com.owing.common.annotation.UseCase;
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

	public List<StorySpellCheckResponse> execute(Long storyId) {
		Story story = storyAdapter.findById(storyId);
		String content = storyDomainService.getParsedContent(story);

		if(content.isBlank()){
			return List.of();
		}

		StorySpellCheckRequest request = StorySpellCheckRequest.of(content);

		return owingAiClient.spellCheck(request);
	}
}
