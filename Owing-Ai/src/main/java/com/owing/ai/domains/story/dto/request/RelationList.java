package com.owing.ai.domains.story.dto.request;

public record RelationList(
	Long relationshipId,
	String label,
	Long sourceId,
	Long targetId,
	String type
) {
}
