package com.owing.node.common.model.projection;

import com.owing.node.domains.cast.model.ConnectionHandle;

public record CastRelationshipProjection (
        Long relationshipId,
        String label,
        Long sourceId,
        ConnectionHandle sourceHandle,
        Long targetId,
        ConnectionHandle targetHandle
) {
}
