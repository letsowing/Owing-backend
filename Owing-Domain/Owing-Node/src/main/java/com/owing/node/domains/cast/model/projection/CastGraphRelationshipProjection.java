package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record CastGraphRelationshipProjection(
        String uuid,
        String type,
        String label,
        Long sourceId,
        Long targetId,
        ConnectionHandle sourceHandle,
        ConnectionHandle targetHandle
) {
    public CastGraphRelationshipProjection {
        type = ConnectionType.of(type).name();
    }
}
