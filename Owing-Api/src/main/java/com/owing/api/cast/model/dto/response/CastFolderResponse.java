package com.owing.api.cast.model.dto.response;

import java.util.List;

public record CastFolderResponse(
        Long id,
        String name,
        String description,
        List<CastFileResponse> files
) {
}
