package com.owing.ai.domains.story.dto.request.crashCheck;

import java.time.LocalDateTime;

public interface StoryInfoDto {
	LocalDateTime updatedAt();
	Long id();
	default boolean isNewContent(LocalDateTime lastUpdated) {
		if(this.updatedAt() == null) {
			return false; //fixme
		}
		return this.updatedAt().isAfter(lastUpdated);
	}
}
