package com.owing.node.domains.cast.model.projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owing.node.domains.cast.model.Coordinate;

public record CastGraphNodeProjection(
        @JsonProperty("id")
        Long castId,
        String name,
        String role,
        String imageUrl,
        Coordinate coordinate
) {
}
