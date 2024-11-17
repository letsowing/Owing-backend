package com.owing.ai.domains.story.dto.request.crashCheck.universe;

import java.time.LocalDateTime;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record UniverseInfo(
	Long id,
	String name,
	String description,
	LocalDateTime updatedAt
) implements StoryInfoDto {
}
