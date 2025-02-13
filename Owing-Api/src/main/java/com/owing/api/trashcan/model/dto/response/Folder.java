package com.owing.api.trashcan.model.dto.response;

import java.util.List;

import com.owing.entity.domains.trashcan.model.TrashCanFolder;

public record Folder(Long id, String name, String description, List<File> files) {
	public static Folder from(TrashCanFolder trashCanFolder) {
		List<File> files = trashCanFolder.getTrashCanList().stream()
			.map(File::from)
			.toList();

		return new Folder(
			trashCanFolder.getId(),
			trashCanFolder.getName(),
			trashCanFolder.getDescription(),
			files
		);
	}
}
