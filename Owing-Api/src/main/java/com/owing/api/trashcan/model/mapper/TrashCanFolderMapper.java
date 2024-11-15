package com.owing.api.trashcan.model.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.common.annotation.Mapper;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.file.model.BaseFileEntity;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.adaptor.TrashCanAdaptor;
import com.owing.entity.domains.trashcan.model.File;
import com.owing.entity.domains.trashcan.model.Folder;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;

import com.owing.node.common.model.BaseFileNode;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class TrashCanFolderMapper {
	private final TrashCanAdaptor trashCanAdaptor;
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;

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

	public <T extends BaseFolder> TrashCanFolder toFolderEntity(T entity, Project project) {
		FolderType folderType = determineTableName(entity);
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findByItemIdAndTableName(entity.getId(), folderType)
			.orElse(newTrashCanFolder(entity, project));

		List<TrashCan> trashCanList = entity.getFiles().stream()
				.map(file -> folderType.isCast()
						? processFileAsNode(file, trashCanFolder)
						: processFileAsEntity(file, trashCanFolder))
				.toList();

		trashCanFolder.getTrashCanList().addAll(trashCanList);

		return trashCanFolder;
	}

	private <T extends BaseFolder> TrashCanFolder newTrashCanFolder(T entity, Project project){
		return TrashCanFolder.builder()
			.itemId(entity.getId())
			.tableName(determineTableName(entity))
			.name(entity.getName())
			.description(entity.getDescription())
			.project(project)
			.trashCanList(new ArrayList<>())
			.build();
	}

	private <T extends BaseFolder> FolderType determineTableName(T entity) {
		return FolderType.fromClassName(entity.getClass().getSimpleName());
	}

	private TrashCan toEntity(BaseFileEntity<?> baseFileEntity, TrashCanFolder trashCanFolder) {
		return TrashCan.builder()
			.trashCanFolder(trashCanFolder)
			.itemId(baseFileEntity.getId())
			.name(baseFileEntity.getName())
			.description(baseFileEntity.getDescription())
			.build();
	}

	private TrashCan toNode(BaseFileNode<?> baseFileNode, TrashCanFolder trashCanFolder) {
		return TrashCan.builder()
				.trashCanFolder(trashCanFolder)
				.itemId(baseFileNode.getId())
				.name(baseFileNode.getName())
				.description(baseFileNode.getDescription())
				.build();
	}

	private TrashCan processFileAsNode(Object file, TrashCanFolder trashCanFolder) {
		return toNode((BaseFileNode<?>) file, trashCanFolder);
	}

	private TrashCan processFileAsEntity(Object file, TrashCanFolder trashCanFolder) {
		return toEntity((BaseFileEntity<?>) file, trashCanFolder);
	}
}
