package com.owing.api.dnd.model.dto.response;

import java.util.List;

import com.owing.core.dnd.base.model.DndFolder;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

// @Builder
// public record FolderInfoResponse(
// 	Long id,
// 	String name,
// 	String description,
// 	Long projectId,
// 	List<FileInfoResponse> files
// ) implements DndInfoResponse {
// 	public static FolderInfoResponse from(BaseFolder entity) {
// 		List<FileInfoResponse> files = entity.getFiles().stream()
// 				.map(FileInfoResponse::from)
// 				.toList();
// 		return FolderInfoResponse.builder()
// 				.id(entity.getId())
// 				.name(entity.getName())
// 				.description(entity.getDescription())
// 				.projectId(entity.getProjectId())
// 				.files(files)
// 				.build();
// 	}
// }

@Getter
@SuperBuilder
public class FolderInfoResponse<T extends FileInfoResponse> implements DndInfoResponse {
	protected Long id;
	protected String name;
	protected String description;
	protected Long projectId;
	protected List<T> files;

	public FolderInfoResponse(Long id, String name, String description, Long projectId, List<T> files) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.projectId = projectId;
		this.files = files;
	}

	public static FolderInfoResponse from(DndFolder entity) {
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
