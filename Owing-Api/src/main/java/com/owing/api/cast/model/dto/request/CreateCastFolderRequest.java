package com.owing.api.cast.model.dto.request;

public record CreateCastFolderRequest(
        Long projectId,
        String name,
        String description
) {
}
