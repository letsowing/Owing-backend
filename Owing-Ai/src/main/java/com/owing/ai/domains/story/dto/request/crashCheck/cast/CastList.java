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

	@Override
	public String toString() {
		return String.format(
			"""
				[등장인물]
				이름 : %s, 역할 : %s, 성별 : %s, 나이 : %d, 설명 : %s
			""",
			name, role, gender, age, description);
	}
}
