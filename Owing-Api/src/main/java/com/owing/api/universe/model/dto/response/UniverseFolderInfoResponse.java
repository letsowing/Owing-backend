package com.owing.api.universe.model.dto.response;

import java.util.List;

import com.owing.api.dnd.dto.response.FolderInfoResponse;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UniverseFolderInfoResponse implements FolderInfoResponse {
	private  Long id;
	private  String name;
	private  String description;
	private  Long projectId;
	private  List<UniverseFileInfoResponse> files;

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
