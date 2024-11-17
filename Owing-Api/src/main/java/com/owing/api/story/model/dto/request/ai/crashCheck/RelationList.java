package com.owing.api.story.model.dto.request;
import java.time.LocalDateTime;

import com.owing.node.domains.cast.model.projection.CastRelationshipAiProjection;

public record RelationList(
	Long id,
	String label,
	Long sourceId,
	Long targetId,
	String type,
	LocalDateTime updatedAt
) {
	public static RelationList from(CastRelationshipAiProjection relationship) {
		return new RelationList(
			relationship.id(),
			relationship.label(),
			relationship.sourceId(),
			relationship.targetId(),
			relationship.type(),
			relationship.updatedAt()
		);
	}
}
