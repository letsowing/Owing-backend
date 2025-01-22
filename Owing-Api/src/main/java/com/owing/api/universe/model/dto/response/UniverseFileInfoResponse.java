package com.owing.api.universe.model.dto.response;

import com.owing.api.dnd.model.dto.response.FileInfoResponse;
import com.owing.entity.domains.universe.model.Universe;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class UniverseFileInfoResponse extends FileInfoResponse {
	private String imageUrl;

	public static UniverseFileInfoResponse from(Universe entity) {
		return UniverseFileInfoResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.folderId(entity.getParentId())
			.imageUrl(entity.getImageUrl())
			.build();
	}
}
