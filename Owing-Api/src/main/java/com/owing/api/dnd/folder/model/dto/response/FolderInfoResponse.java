package com.owing.api.dnd.folder.model.dto.response;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;
import lombok.Builder;

import java.util.List;

@Builder
public record FolderInfoResponse(
	Long id,
	String name,
	String description,
	Long projectId,
	List<FileInfoResponse> files
) implements DndInfoResponse {
	public static FolderInfoResponse from(BaseFolder entity) {
		List<FileInfoResponse> files = entity.getFiles().stream()
				.map(FileInfoResponse::from)
				.toList();
		return FolderInfoResponse.builder()
				.id(entity.getId())
				.name(entity.getName())
				.description(entity.getDescription())
				.projectId(entity.getProjectId())
				.files(files)
				.build();
	}
}
