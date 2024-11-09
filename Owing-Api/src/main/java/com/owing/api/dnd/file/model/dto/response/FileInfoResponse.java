package com.owing.api.dnd.file.model.dto.response;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.entity.dnd.file.model.BaseFile;

import lombok.Builder;

@Builder
public record FileInfoResponse(
	Long id,
	String title,
	String description,
	Long folderId
) implements DndInfoResponse {
	public static FileInfoResponse from(BaseFile entity) {
		return FileInfoResponse.builder()
			.id(entity.getId())
			.title(entity.getTitle())
			.description(entity.getDescription())
			.folderId(entity.getParentId())
			.build();
	}
}
