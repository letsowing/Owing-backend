package com.owing.api.cast.model.dto.response;

import com.owing.api.dnd.dto.response.FileInfoResponse;

public record CastInfoWithFolderResponse(
        Long folderId,
        CastInfoResponse cast
) implements FileInfoResponse {
}
