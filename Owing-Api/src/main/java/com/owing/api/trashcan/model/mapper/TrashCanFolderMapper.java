package com.owing.api.trashcan.model.mapper;

import java.util.ArrayList;
import java.util.List;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.common.annotation.Mapper;
import com.owing.entity.dnd.file.model.DndFileEntity;
import com.owing.core.dnd.folder.model.DndFolder;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.trashcan.model.File;
import com.owing.entity.domains.trashcan.model.Folder;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;

import com.owing.node.common.model.DndFileNode;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class TrashCanFolderMapper {
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final UniverseAdapter universeAdapter;
	private final CastNodeAdapter castNodeAdapter;

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
				trashCan.getDescription(),
					trashCan.getImageUrl()
			))
			.toList();

		return new Folder(
			trashCanFolder.getId(),
			trashCanFolder.getName(),
			trashCanFolder.getDescription(),
			files
		);
	}

	public <T extends DndFolder> TrashCanFolder toFolderEntity(T entity, Project project) {
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

	private <T extends DndFolder> TrashCanFolder newTrashCanFolder(T entity, Project project){
		return TrashCanFolder.builder()
			.itemId(entity.getId())
			.tableName(determineTableName(entity))
			.name(entity.getName())
			.description(entity.getDescription())
			.project(project)
			.trashCanList(new ArrayList<>())
			.build();
	}

	private <T extends DndFolder> FolderType determineTableName(T entity) {
		return FolderType.fromClassName(entity.getClass().getSimpleName());
	}

	private TrashCan toEntity(DndFileEntity<?> entity, TrashCanFolder trashCanFolder) {
		String imageUrl = GetImageUrl(trashCanFolder.getTableName(), entity.getId());
		return TrashCan.builder()
			.trashCanFolder(trashCanFolder)
			.itemId(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
				.imageUrl(imageUrl)
			.build();
	}

	private String GetImageUrl(FolderType folderType, Long id) {
		return switch (folderType) {
			case UNIVERSE -> universeAdapter.findImageUrlById(id);
			case STORY -> null;
			case CAST -> castNodeAdapter.findImageUrlById(id);
		};
	}

	private TrashCan toNode(DndFileNode<?> node, TrashCanFolder trashCanFolder) {
		String imageUrl = castNodeAdapter.findImageUrlById(node.getId());
		return TrashCan.builder()
				.trashCanFolder(trashCanFolder)
				.itemId(node.getId())
				.name(node.getName())
				.description(node.getDescription())
				.imageUrl(imageUrl)
				.build();
	}

	private TrashCan processFileAsNode(Object file, TrashCanFolder trashCanFolder) {
		return toNode((DndFileNode<?>) file, trashCanFolder);
	}

	private TrashCan processFileAsEntity(Object file, TrashCanFolder trashCanFolder) {
		return toEntity((DndFileEntity<?>) file, trashCanFolder);
	}
}
