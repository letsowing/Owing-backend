package com.owing.api.story.model.dto.request;

import com.owing.entity.domains.story.model.Story;

public record PrevStoryInfo(
	Long storyId,
	String name,
	String description,
	String episode
) {
	public static PrevStoryInfo from(Story story) {
		return new PrevStoryInfo(
			story.getId(),
			story.getName(),
			story.getDescription(),
			story.getContent()
		);
	}
}
