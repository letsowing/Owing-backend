package com.owing.api.dnd.dto.common.request;

import com.owing.api.dnd.dto.request.UpdateFilePositionRequest;

public record CommonUpdateFilePositionRequest(
	Long beforeId,
	Long afterId,
	Long folderId
)
implements UpdateFilePositionRequest {
}
