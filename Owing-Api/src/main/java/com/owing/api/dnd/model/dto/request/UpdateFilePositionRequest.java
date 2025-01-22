package com.owing.api.dnd.model.dto.request;

public record UpdateFilePositionRequest(
	Long beforeId,
	Long afterId,
	Long folderId
) implements UpdateDndPositionRequest {
}
