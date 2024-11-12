package com.owing.api.story.model.dto.request;

public record UpdateStoryRequest(
	String name,
	String description
) {
}
