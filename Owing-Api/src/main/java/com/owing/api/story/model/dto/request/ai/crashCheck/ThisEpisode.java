package com.owing.api.story.model.dto.request.ai.crashCheck;

import java.time.LocalDateTime;

import com.owing.entity.domains.story.model.dto.StoryVO;

public record ThisEpisode(
	Long id,
	String content,
	LocalDateTime updatedAt
) {

	public static ThisEpisode from(StoryVO story) {
        return new ThisEpisode(
			story.id(),
			story.episode(),
			story.updatedAt()
		);
    }
}
