package com.owing.api.project.model.dto.response;

public record ProjectResponse(
        Long id,
        String title,
        String presignedUrl
) {
}
