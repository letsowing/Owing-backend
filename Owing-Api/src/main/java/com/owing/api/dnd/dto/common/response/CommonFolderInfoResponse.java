package com.owing.api.dnd.dto.common.response;

import java.util.List;

import com.owing.api.dnd.dto.response.FolderInfoResponse;
import com.owing.core.dnd.model.DndFolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonFolderInfoResponse implements FolderInfoResponse {
	protected Long id;
	protected String name;
	protected String description;
	protected Long projectId;
	protected List<CommonFileInfoResponse> files;

	public static CommonFolderInfoResponse from(DndFolder entity) {
		List<CommonFileInfoResponse> files = entity.getFiles().stream()
			.map(CommonFileInfoResponse::from)
			.toList();
		return CommonFolderInfoResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.projectId(entity.getProjectId())
			.files(files)
			.build();
	}
}
