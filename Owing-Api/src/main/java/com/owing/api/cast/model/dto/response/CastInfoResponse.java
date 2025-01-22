package com.owing.api.cast.model.dto.response;

import com.owing.api.dnd.model.dto.response.DndInfoResponse;
import com.owing.node.domains.cast.model.Coordinate;

public record CastInfoResponse(
        Long id,
        String name,
        Long age,
        String gender,
        String role,
        String description,
        String imageUrl,
        Coordinate coordinate
) implements DndInfoResponse {
}
