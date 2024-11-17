package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.time.LocalDateTime;

import com.owing.entity.domains.story.model.Story;

public record ThisEpisode(
	Long id,
	String content,
	LocalDateTime updatedAt
) {

	public static ThisEpisode from(Story story) {
        return new ThisEpisode(story.getId(), story.getContent(), story.getUpdatedAt());
    }
}
