package com.owing.api.story.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record StorySpellCheckLogResponse(
	Long id,
	List<StorySpellCheckResponse> storySpellCheckResponseList,
	LocalDateTime createdAt
) {
}
