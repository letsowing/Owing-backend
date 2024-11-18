package com.owing.node.domains.cast.model.projection;

import java.time.LocalDateTime;

public record CastAiProjection(
        Long castId,
        String name,
        String role,
        String gender,
        Long age,
        String description,
		LocalDateTime updatedAt
) {
}
