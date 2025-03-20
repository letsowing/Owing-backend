package com.owing.api.dnd.dto.common.request;

import com.owing.api.dnd.dto.request.UpdateFolderPositionRequest;

public record CommonUpdateFolderPositionRequest(
	Long beforeId,
	Long afterId,
	Long projectId
)
implements UpdateFolderPositionRequest {
}
