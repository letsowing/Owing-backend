package com.owing.api.dnd.folder.model.dto.request;

import com.owing.api.dnd.base.model.dto.request.UpdateDndPositionRequest;

public record UpdateFolderPositionRequest(
	Long position,
	Long projectId
) implements UpdateDndPositionRequest {
}
