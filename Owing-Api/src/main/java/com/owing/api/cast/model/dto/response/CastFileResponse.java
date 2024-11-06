package com.owing.api.cast.model.dto.response;

public record CastFileResponse(
        Long id,
        String name,
        String description,
        String imageUrl,
        Long position
) {
}
