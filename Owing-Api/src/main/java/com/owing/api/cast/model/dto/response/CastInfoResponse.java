package com.owing.api.cast.model.dto.response;

import com.owing.api.dnd.dto.response.FileInfoResponse;
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
) implements FileInfoResponse {
}
