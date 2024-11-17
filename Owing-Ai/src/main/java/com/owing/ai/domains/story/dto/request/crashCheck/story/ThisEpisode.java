package com.owing.ai.domains.story.dto.request.crashCheck.story;

import java.time.LocalDateTime;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record ThisEpisode(
	Long id,
	String content,
	LocalDateTime updatedAt
) implements StoryInfoDto {
}
