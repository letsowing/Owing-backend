package com.owing.api.dnd.dto.request;

public interface UpdateFilePositionRequest {
	Long beforeId();
	Long afterId();
	Long folderId();
}
