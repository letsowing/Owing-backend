package com.owing.ai.domains.story.dto.request;

public record PrevStoryInfo(
	Long storyId,
	String name,
	String description,
	String episode
) {
}
