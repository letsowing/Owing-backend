package com.owing.api.cast.model.dto.request;

import com.owing.node.domains.cast.model.Coordinate;

public record CreateCastRequest(
        Long folderId,
        String name,
        Long age,
        String gender,
        String role,
        String description,
        String imageUrl,
        Coordinate coordinate
) {
}
