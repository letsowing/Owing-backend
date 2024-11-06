package com.owing.api.cast.model.dto.response;

public record CastFolderInfoResponse(
        Long id,
        String name,
        String description,
        Long position
) {
}
