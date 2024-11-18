package com.owing.ai.domains.story.dto.request.crashCheck.universe;

import java.time.LocalDateTime;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record UniverseInfo(
	Long id,
	String name,
	String description,
	LocalDateTime updatedAt
) implements StoryInfoDto {
	@Override
	public String toString() {
		return String.format(
			"""
				[세계관]
				[제목] : %s, 
				[설명] : %s
			""",
			name, description);
	}
}
