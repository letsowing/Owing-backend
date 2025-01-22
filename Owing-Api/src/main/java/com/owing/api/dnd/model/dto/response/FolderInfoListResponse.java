package com.owing.api.dnd.model.dto.response;

import java.util.List;

public record FolderInfoListResponse(
        List<FolderInfoResponse> folderList
) implements DndInfoListResponse {
}
