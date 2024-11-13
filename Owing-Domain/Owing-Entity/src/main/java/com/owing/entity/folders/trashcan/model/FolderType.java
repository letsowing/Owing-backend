package com.owing.entity.folders.trashcan.model;

public enum FolderType {
	STORY,
	CAST,
	UNIVERSE;

	public static FolderType fromClassName(String className) {
		if (className == null || className.isEmpty()) {
			throw new IllegalArgumentException("Class name cannot be null or empty");
		}

		String upperCaseName = className.toUpperCase();

		return switch (upperCaseName) {
			case "STORYFOLDER" -> STORY;
			case "CASTFOLDER" -> CAST;
			case "UNIVERSEFOLDER" -> UNIVERSE;
			default -> throw new IllegalArgumentException("Unknown class name: " + className);
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
