package com.owing.api.cast.model.dto.request;

import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record UpdateCastRelationshipRequest(
        ConnectionType type,
        Long sourceId,
        Long targetId,
        ConnectionHandle sourceHandle,
        ConnectionHandle targetHandle
) {
}
