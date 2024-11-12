package com.owing.node.domains.cast.model.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;

public record CastGraphRelationshipProjection(
        @JsonProperty("id")
        Long relationshipId,
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
