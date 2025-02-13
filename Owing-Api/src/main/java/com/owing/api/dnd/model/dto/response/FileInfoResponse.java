package com.owing.api.dnd.model.dto.response;

import com.owing.core.dnd.model.DndFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoResponse implements DndInfoResponse {
	protected Long id;
	protected String name;
	protected String description;
	protected Long folderId;

	public static FileInfoResponse from(DndFile entity) {
		return FileInfoResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.folderId(entity.getParentId())
			.build();
	}
}
