package com.owing.api.trashcan.model.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.common.annotation.Mapper;
import com.owing.core.dnd.file.model.BaseFileEntity;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.model.File;
import com.owing.entity.domains.trashcan.model.Folder;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;

@Mapper
public class TrashCanFolderMapper {
	public TrashCanFolderResponse toTrashCanFolderResponse(List<TrashCanFolder> trashCanFolders) {
		List<Folder> story = new ArrayList<>();
		List<Folder> cast = new ArrayList<>();
		List<Folder> universe = new ArrayList<>();

		for (TrashCanFolder trashCanFolder : trashCanFolders) {
			switch (trashCanFolder.getTableName()) {
				case STORY -> story.add(toFolder(trashCanFolder));
				case CAST -> cast.add(toFolder(trashCanFolder));
				case UNIVERSE -> universe.add(toFolder(trashCanFolder));
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

	public <T extends BaseFolder> TrashCanFolder toFolderEntity(T entity, Project project) {
		return TrashCanFolder.builder()
			.itemId(entity.getId())
			.tableName(determineTableName(entity))
			.name(entity.getName())
			.description(entity.getDescription())
			.project(project)
			.trashCanList(entity.getFiles().stream()
				.map(file -> toEntity((BaseFileEntity<?>) file))
				.collect(Collectors.toList()))
			.build();
	}

	private <T extends BaseFolder> FolderType determineTableName(T entity) {
		return FolderType.fromClassName(entity.getClass().getSimpleName());
	}

	private TrashCan toEntity(BaseFileEntity<?> baseFileEntity) {
		return TrashCan.builder()
			.itemId(baseFileEntity.getId())
			.name(baseFileEntity.getName())
			.description(baseFileEntity.getDescription())
			.build();
	}
}
