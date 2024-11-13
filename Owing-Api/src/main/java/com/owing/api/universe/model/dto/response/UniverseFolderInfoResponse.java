package com.owing.api.universe.model.dto.response;

import java.util.List;

import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UniverseFolderInfoResponse extends FolderInfoResponse<UniverseFileInfoResponse> {

	public static UniverseFolderInfoResponse from(UniverseFolder entity) {
		List<UniverseFileInfoResponse> files = entity.getFiles().stream()
			.map(UniverseFileInfoResponse::from)
			.toList();
		return UniverseFolderInfoResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.projectId(entity.getProjectId())
			.files(files)
			.build();
	}
}
