package com.owing.api.trashcan.model.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.owing.api.trashcan.model.dto.File;
import com.owing.api.trashcan.model.dto.Folder;
import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;

@Mapper
public class TrashCanMapper {

	public TrashCanFolderResponse toTrashCanFolderResponse(List<TrashCanFolder> trashCanFolders) {
		List<Folder> story = new ArrayList<>();
		List<Folder> cast = new ArrayList<>();
		List<Folder> universe = new ArrayList<>();

		for (TrashCanFolder trashCanFolder : trashCanFolders) {
			if(isStoryCategory(trashCanFolder)){
				story.add(toFolder(trashCanFolder));
			}
			else if (isCastCategory(trashCanFolder)){
				cast.add(toFolder(trashCanFolder));
			}
			else if (isUniverseCategory(trashCanFolder)){
				universe.add(toFolder(trashCanFolder));
			}
		}

		return new TrashCanFolderResponse(story, cast, universe);
	}

	private Folder toFolder(TrashCanFolder trashCanFolder) {
		List<File> files = trashCanFolder.getTrashCanList().stream()
			.map(trashCan -> new File(
				trashCan.getId(),
				trashCan.getName(),
				trashCan.getDescription()
			))
			.toList();

		return new Folder(
			trashCanFolder.getId(),
			trashCanFolder.getName(),
			trashCanFolder.getDescription(),
			files
		);
	}

	private boolean isStoryCategory(TrashCanFolder trashCanFolder) {
		return trashCanFolder.getTableName().isStory();
	}

	private boolean isCastCategory(TrashCanFolder trashCanFolder) {
		return trashCanFolder.getTableName().isCast();
	}

	private boolean isUniverseCategory(TrashCanFolder trashCanFolder) {
		return trashCanFolder.getTableName().isUniverse();
	}
}
