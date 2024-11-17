package com.owing.ai.domains.story.dto.request.crashCheck.cast;

import java.time.LocalDateTime;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record CastList(
	Long id,
	String name,
	String role,
	String gender,
	Long age,
	String description,
	LocalDateTime updatedAt
) implements StoryInfoDto {
}
