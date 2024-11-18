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

	@Override
	public String toString() {
		return String.format(
			"""
				[관계]
				설명 : %s, 
				소스 : %d, 
				타겟 : %d, 
				방향(단,양) : %s
			""",
			label, sourceId, targetId, type);
	}
}
