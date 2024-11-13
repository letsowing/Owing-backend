package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.ConnectionType;

public record CastRelationshipAiProjection(
        String type,
        String label,
        Long sourceId,
        Long targetId
) {
    public CastRelationshipAiProjection {
        type = ConnectionType.of(type).name();
    }
}
