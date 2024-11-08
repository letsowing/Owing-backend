package com.owing.api.dnd.folder.model.dto.response;

import java.util.List;

import com.owing.api.dnd.base.model.dto.response.DndInfoListResponse;

public record FolderInfoListResponse(
        List<FolderInfoResponse> folderList
) implements DndInfoListResponse {
}
