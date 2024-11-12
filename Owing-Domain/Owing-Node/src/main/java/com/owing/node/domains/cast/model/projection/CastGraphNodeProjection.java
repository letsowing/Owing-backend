package com.owing.node.domains.cast.model.projection;

public record CastGraphNodeProjection(
        Long id,
        String name,
        String gender,
        String imageUrl
) {
}
