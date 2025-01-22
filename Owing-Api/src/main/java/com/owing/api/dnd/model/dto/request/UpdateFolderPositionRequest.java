package com.owing.api.dnd.model.dto.request;

public record UpdateFolderPositionRequest(
	Long beforeId,
	Long afterId,
	Long projectId
) implements UpdateDndPositionRequest {
}
