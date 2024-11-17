package com.owing.api.cast.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record CastRelationshipInfoResponse(
        Long id,
        String label,
        ConnectionType type,
        @JsonProperty("source")
        Long sourceId,
        ConnectionHandle sourceHandle,
        @JsonProperty("target")
        Long targetId,
        ConnectionHandle targetHandle
) {
}
