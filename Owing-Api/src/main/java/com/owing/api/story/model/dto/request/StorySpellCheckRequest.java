package com.owing.api.story.model.dto.request;

public record StorySpellCheckRequest(
	String content
) {
	public static StorySpellCheckRequest of(String content) {
		return new StorySpellCheckRequest(content);
	}
}
