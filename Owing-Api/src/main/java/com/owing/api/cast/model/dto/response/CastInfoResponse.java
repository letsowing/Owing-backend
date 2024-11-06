package com.owing.api.cast.model.dto.response;

import com.owing.node.domains.cast.model.Coordinate;
import lombok.Builder;

public record CastInfoResponse(
        Long id,
        String name,
        Long age,
        String gender,
        String role,
        String description,
        String imageUrl,
        Coordinate coordinate,
        Long position
) {
}
