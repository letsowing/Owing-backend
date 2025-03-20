package com.owing.api.dnd.dto.request;

public interface UpdateFolderPositionRequest {
	Long beforeId();
	Long afterId();
	Long projectId();
}
