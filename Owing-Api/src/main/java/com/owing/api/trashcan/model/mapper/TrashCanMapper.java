package com.owing.api.trashcan.model.mapper;

import com.owing.common.annotation.Mapper;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.repository.StoryRepository;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.universe.repository.UniverseRepository;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class TrashCanMapper {
	final private TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final UniverseRepository universeRepository;
	private final StoryRepository storyRepository;
	private final ProjectAdapter projectAdapter;

	public <T extends BaseFile<?>> TrashCan toEntity(T entity) {
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findByItemId(entity.getParentId())
			.orElse(
				newTrashCanFolder(entity)
			);
		return TrashCan.builder()
			.itemId(entity.getId())
			.name(entity.getTitle())
			.description(entity.getDescription())
			.trashCanFolder(trashCanFolder)
			.build();
	}

	private <T extends BaseFile<?>> TrashCanFolder newTrashCanFolder(T entity){
		FolderType folderType = determineTableName(entity);
		BaseFolder baseFolder = getBaseFolderByRepository(folderType, entity.getParentId());
		Project project = projectAdapter.findById(baseFolder.getProjectId());

		return TrashCanFolder.builder()
			.itemId(entity.getParentId())
			.tableName(folderType)
			.name(baseFolder.getName())
			.description(baseFolder.getDescription())
			.project(project)
			.trashCanList(null)
			.build();
	}

	private <T extends BaseFile<?>> FolderType determineTableName(T entity) {
		String className = entity.getClass().getSimpleName().toLowerCase();

		if (className.contains("story")) {
			return FolderType.STORY;
		} else if (className.contains("cast")) {
			return FolderType.CAST;
		} else if (className.contains("universe")) {
			return FolderType.UNIVERSE;
		}

		throw new IllegalArgumentException("todo Unknown folder type for class: " + className);
	}

	private BaseFolder getBaseFolderByRepository(FolderType folderType, Long parentId) {
		return switch (folderType) {
			case UNIVERSE -> (BaseFolder) universeRepository.findById(parentId)
				.orElseThrow(() -> new IllegalStateException("todo Universe not found for ID: " + parentId));
			case STORY -> (BaseFolder) storyRepository.findById(parentId)
				.orElseThrow(() -> new IllegalStateException("todo Story not found for ID: " + parentId));
			default -> throw new IllegalArgumentException("todo Unsupported FolderType: " + folderType);
		};
	}
}
