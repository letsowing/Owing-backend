package com.owing.api.trashcan.model.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.owing.entity.domains.trashcan.model.TrashCanFolder;

public record TrashCanFolderListResponse(List<Folder> story, List<Folder> cast, List<Folder> universe) {
	public static TrashCanFolderListResponse from(List<TrashCanFolder> trashCanFolders) {
		List<Folder> story = new ArrayList<>();
		List<Folder> cast = new ArrayList<>();
		List<Folder> universe = new ArrayList<>();

		for (TrashCanFolder trashCanFolder : trashCanFolders) {
			switch (trashCanFolder.getTableName()) {
				case STORY -> story.add(Folder.from(trashCanFolder));
				case CAST -> cast.add(Folder.from(trashCanFolder));
				case UNIVERSE -> universe.add(Folder.from(trashCanFolder));
			}
		}

		return new TrashCanFolderListResponse(story, cast, universe);
	}
}
