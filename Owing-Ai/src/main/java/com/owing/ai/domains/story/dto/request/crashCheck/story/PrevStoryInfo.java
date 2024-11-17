package com.owing.ai.domains.story.dto.request.crashCheck.story;

import java.time.LocalDateTime;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record PrevStoryInfo(
	Long id,
	String name,
	String description,
	String episode,
	LocalDateTime updatedAt
) implements StoryInfoDto {
}
