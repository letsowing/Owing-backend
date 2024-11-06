package com.owing.api.cast.model.dto.request;

import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record CreateConnectionRequest(
        String label,
        ConnectionType type,
        Long sourceId,
        ConnectionHandle sourceHandle,
        Long targetId,
        ConnectionHandle targetHandle
) {
}
