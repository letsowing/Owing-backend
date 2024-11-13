package com.owing.node.domains.cast.model.projection;

public record CastAiProjection(
        Long castId,
        String name,
        String role,
        String gender,
        Long age,
        String description
) {
}
