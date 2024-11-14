package com.owing.ai.domains.story.dto.request;

public record CastList(
	Long castId,
	String name,
	String role,
	String gender,
	Long age,
	String description
) {
}
