package com.owing.node.domains.cast.model.projection;

import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record CastConnectionProjection(
        String uuid,
        String type,
        String label,
        Long sourceId,
        Long targetId,
        ConnectionHandle sourceHandle,
        ConnectionHandle targetHandle
) {
    public CastConnectionProjection {
        type = ConnectionType.of(type).name();
    }
}
