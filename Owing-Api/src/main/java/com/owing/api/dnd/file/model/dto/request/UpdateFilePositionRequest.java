package com.owing.api.dnd.file.model.dto.request;

import com.owing.api.dnd.base.model.dto.request.UpdateDndPositionRequest;

public record UpdateFilePositionRequest(
	Long beforeId,
	Long afterId,
	Long folderId
) implements UpdateDndPositionRequest {
}
