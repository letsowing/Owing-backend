package com.owing.api.dnd.dto.common.response;

import com.owing.core.dnd.model.DndFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonFileInfoResponse {
	private Long id;
	private String name;
	private String description;
	private Long folderId;

	public static CommonFileInfoResponse from(DndFile entity) {
		return CommonFileInfoResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.folderId(entity.getParentId())
			.build();
	}
}
