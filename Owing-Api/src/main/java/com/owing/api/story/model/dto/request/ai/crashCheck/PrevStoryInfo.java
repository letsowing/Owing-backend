package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.time.LocalDateTime;

import com.owing.entity.domains.story.model.dto.StoryVO;

public record PrevStoryInfo(
	Long id,
	String name,
	String description,
	String episode,
	LocalDateTime updatedAt
) {
	public static PrevStoryInfo from(StoryVO story) {
		return new PrevStoryInfo(
			story.id(),
			story.name(),
			story.description(),
			story.episode(),
			story.updatedAt()
		);
	}
}
