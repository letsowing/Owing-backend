package com.owing.node.common.model.projection;

import com.owing.node.domains.cast.model.ConnectionHandle;

public record CastRelationshipProjection (
        String relationshipId,
        String label,
        Long sourceId,
        Long targetId,
        ConnectionHandle sourceHandle,
        ConnectionHandle targetHandle
) {
}
