package com.owing.api.dnd.model.dto.response;

import java.util.List;

public record FileInfoListResponse(
        List<FileInfoResponse> fileList
) implements DndInfoListResponse {
}
