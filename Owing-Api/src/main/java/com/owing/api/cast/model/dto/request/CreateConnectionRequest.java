package com.owing.api.cast.model.dto.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record CreateConnectionRequest(
        String label,
        ConnectionType type,
        @JsonAlias("source")
        Long sourceId,
        ConnectionHandle sourceHandle,
        @JsonAlias("target")
        Long targetId,
        ConnectionHandle targetHandle
) {
}
