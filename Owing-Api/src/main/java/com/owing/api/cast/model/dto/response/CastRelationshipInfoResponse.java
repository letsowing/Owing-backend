package com.owing.api.cast.model.dto.response;

import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record CastRelationshipInfoResponse(
        String id,
        String label,
        ConnectionType type,
        Long sourceId,
        ConnectionHandle sourceHandle,
        Long targetId,
        ConnectionHandle targetHandle
) {
}
