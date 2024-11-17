package com.owing.ai.domains.story.dto.request.crashCheck.project;

import java.time.LocalDateTime;
import java.util.List;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record ProjectInfoDto(
	Long id,
	String title,
	String description,
	String category,
	List<String> genres,
	LocalDateTime updatedAt
) implements StoryInfoDto {
}
