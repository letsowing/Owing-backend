package com.owing.node.domains.cast.model.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CastGraphNodeProjection(
        @JsonProperty("id")
        Long castId,
        String name,
        String gender,
        String imageUrl
) {
}
