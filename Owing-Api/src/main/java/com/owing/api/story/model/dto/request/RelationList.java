package com.owing.api.story.model.dto.request;

import com.owing.node.domains.cast.model.projection.CastRelationshipAiProjection;

public record RelationList(
	String label,
	Long sourceId,
	Long targetId,
	String type
) {
	public static RelationList from(CastRelationshipAiProjection relationship) {
		return new RelationList(
			relationship.label(),
			relationship.sourceId(),
			relationship.targetId(),
			relationship.type()
		);
	}
}
