package com.owing.node.domains.cast.model.projection;

import java.time.LocalDateTime;

import com.owing.node.domains.cast.model.ConnectionType;

public record CastRelationshipAiProjection(
        Long id,
        String type,
        String label,
        Long sourceId,
        Long targetId,
        LocalDateTime updatedAt
) {
    public CastRelationshipAiProjection {
        type = ConnectionType.of(type).name();
    }
}
