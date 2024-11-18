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
	@Override
	public String toString() {
		return String.format(
			"""
				[작품 정보]
				[제목] : %s, 
				[설명] : %s, 
				[카테고리] : %s, 
				[장르] : %s
			""",
			title, description, category, genres);
	}
}
