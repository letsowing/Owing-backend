package com.owing.api.cast.model.dto.request;

public record UpdateCastInfoRequest(
        Long folderId,
        String name,
        Long age,
        String gender,
        String role,
        String description,
        String imageUrl
) {
}
