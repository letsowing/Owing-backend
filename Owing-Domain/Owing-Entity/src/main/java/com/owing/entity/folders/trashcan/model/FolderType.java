package com.owing.entity.folders.trashcan.model;

public enum FolderType {
	STORY,
	CAST,
	UNIVERSE;

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
