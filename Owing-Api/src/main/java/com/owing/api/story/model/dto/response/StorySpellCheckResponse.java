package com.owing.api.story.model.dto.response;

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
