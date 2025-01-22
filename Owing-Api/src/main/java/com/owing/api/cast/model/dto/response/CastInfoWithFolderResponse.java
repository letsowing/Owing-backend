package com.owing.api.cast.model.dto.response;

import com.owing.api.dnd.model.dto.response.DndInfoResponse;

public record CastInfoWithFolderResponse(
        Long folderId,
        CastInfoResponse cast
) implements DndInfoResponse {
}
