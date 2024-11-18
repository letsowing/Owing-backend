package com.owing.entity.domains.story.model.dto;

import java.time.LocalDateTime;

import com.owing.entity.domains.story.model.Story;

public record StoryVO(
	Long id,
	String name,
	String description,
	String episode,
	LocalDateTime updatedAt
) {
	public static StoryVO from(Story story, String content) {
		return new StoryVO(
			story.getId(),
			story.getName(),
			story.getDescription(),
			content,
			story.getUpdatedAt()
		);
	}
}
