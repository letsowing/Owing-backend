package com.owing.api.story.service;

import org.springframework.transaction.annotation.Transactional;

import com.owing.api.story.model.dto.request.UpdateStoryRequest;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateStoryUseCase {
	private final StoryAdapter storyAdapter;
	private final StoryMapper storyMapper;

	@Transactional("jpaTransactionManager")
	public void execute(Long storyId, UpdateStoryRequest request) {
		Story story = storyAdapter.findById(storyId);
		Story newStory = storyMapper.toEntity(request);
		story.update(newStory);
	}
}
