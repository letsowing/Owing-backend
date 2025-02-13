package com.owing.entity.domains.trashcan.model;

import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;
import com.owing.entity.domains.trashcan.error.TrashCanErrorCode;
import com.owing.entity.domains.trashcan.error.exception.TrashCanException;

public enum FolderType {
	STORY,
	CAST,
	UNIVERSE;

	public static FolderType fromClassName(DndFolder folder) {
		String className = folder.getClass().getSimpleName().toUpperCase();

		return switch (className) {
			case "STORYFOLDER" -> STORY;
			case "CASTFOLDERNODE" -> CAST;
			case "UNIVERSEFOLDER" -> UNIVERSE;
			default -> throw new IllegalArgumentException("Unknown class name: " + className);
		};
	}

	public static FolderType fromClassName(DndFile file) {
		String className = file.getClass().getSimpleName().toUpperCase();

		return switch (className) {
			case "STORY" -> STORY;
			case "CAST" -> CAST;
			case "UNIVERSE" -> UNIVERSE;
			default -> throw TrashCanException.of(TrashCanErrorCode.TRASH_CAN_PARENT_NOT_FOUND);
		};
	}

	public boolean isStory() {
		return this == STORY;
	}

	public boolean isCast() {
		return this == CAST;
	}

	public boolean isUniverse() {
		return this == UNIVERSE;
	}
}
