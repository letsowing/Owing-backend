package com.owing.api.dnd.folder.model.dto.response;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;

import com.owing.core.dnd.folder.model.BaseFolder;
import lombok.Builder;

@Builder
public record FolderInfoResponse(
	Long id,
	String name,
	String description,
	Long projectId
) implements DndInfoResponse {
	public static FolderInfoResponse from(BaseFolder entity) {
		return FolderInfoResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.projectId(entity.getProjectId())
			.build();
	}
}
