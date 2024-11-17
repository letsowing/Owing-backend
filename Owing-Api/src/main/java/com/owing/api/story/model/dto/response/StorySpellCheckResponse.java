package com.owing.api.story.model.dto.response;

import lombok.Builder;

@Builder
public record StorySpellCheckResponse(
	String help,
	Integer errorIdx,
	Integer correctMethod,
	Integer start,
	String errMsg,
	Integer end,
	String orgStr,
	String candWord
) {
}
