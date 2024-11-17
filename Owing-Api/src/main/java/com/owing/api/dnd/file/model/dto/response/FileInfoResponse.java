package com.owing.api.dnd.file.model.dto.response;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.core.dnd.file.model.BaseFile;

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

	public static FileInfoResponse from(BaseFile entity) {
		return FileInfoResponse.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.folderId(entity.getParentId())
			.build();
	}
}
