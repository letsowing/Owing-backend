package com.owing.api.dnd.file.model.dto.response;

import java.util.List;

import com.owing.api.dnd.base.model.dto.response.DndInfoListResponse;

public record FileInfoListResponse(
        List<FileInfoResponse> folderList
) implements DndInfoListResponse {
}
