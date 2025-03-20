package com.owing.api.trashcan.model.dto.response;

import java.util.List;

import com.owing.entity.domains.trashcan.model.FolderType;

public record TrashCanFolderResponse(
	Long id,
	Long itemId,
	FolderType tableName,
	String name,
	String description,
	List<TrashCanResponse>trashCanList
) {
	public static TrashCanFolderResponse from(com.owing.entity.domains.trashcan.model.TrashCanFolder trashCanFolder) {
		return new TrashCanFolderResponse(
			trashCanFolder.getId(),
			trashCanFolder.getItemId(),
			trashCanFolder.getTableName(),
			trashCanFolder.getName(),
			trashCanFolder.getDescription(),
			trashCanFolder.getTrashCanList().stream().map(TrashCanResponse::from).toList()
		);
	}
}