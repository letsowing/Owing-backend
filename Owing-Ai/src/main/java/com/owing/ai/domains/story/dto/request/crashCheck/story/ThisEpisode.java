package com.owing.ai.domains.story.dto.request.crashCheck.story;

import java.time.LocalDateTime;

import com.owing.ai.domains.story.dto.request.crashCheck.StoryInfoDto;

public record ThisEpisode(
	Long id,
	String name,
	String description,
	String content,
	LocalDateTime updatedAt
) implements StoryInfoDto {
	@Override
	public String toString() {
		return String.format(
			"""
				[원고]
				[제목] : %s, 
				[설명] : %s, 
				[내용] : %s
			""",
			name, description, content);
	}
}
