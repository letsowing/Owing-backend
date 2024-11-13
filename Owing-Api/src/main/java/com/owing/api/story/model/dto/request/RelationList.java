package com.owing.api.story.model.dto.request;

import com.owing.node.domains.cast.model.CastRelationship;

public record RelationList(
	Long relationshipId,
	String label,
	Long sourceId,
	Long targetId,
	String type
) {
	public static RelationList from(CastRelationship relationship) {
		return new RelationList(
			relationship.getId(),
			relationship.getLabel(),
			relationship.getSourceId(),
			relationship.getTargetId(),
			null
		);
	}
}
