package com.owing.ai.domains.story.dto.request.crashCheck.cast;

import java.time.LocalDateTime;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record RelationList(
	Long id,
	String label,
	Long sourceId,
	Long targetId,
	String type,
	LocalDateTime updatedAt
) implements StoryInfoDto {
}
