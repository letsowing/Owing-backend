package com.owing.api.story.model.dto.request;
import java.time.LocalDateTime;

import com.owing.entity.domains.story.model.Story;

public record PrevStoryInfo(
	Long id,
	String name,
	String description,
	String episode,
	LocalDateTime updatedAt
) {
	public static PrevStoryInfo from(Story story) {
		return new PrevStoryInfo(
			story.getId(),
			story.getName(),
			story.getDescription(),
			story.getContent(),
			story.getUpdatedAt()
		);
	}
}
