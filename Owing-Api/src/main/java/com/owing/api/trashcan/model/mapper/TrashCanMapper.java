package com.owing.api.trashcan.model.mapper;

import com.owing.common.annotation.Mapper;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;
import com.owing.entity.folders.trashcan.repository.TrashCanFolderRepository;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;

import lombok.RequiredArgsConstructor;

@Mapper
@RequiredArgsConstructor
public class TrashCanMapper {
	final private TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final UniverseFolderAdapter universeFolderAdapter;
	private final StoryFolderAdapter storyFolderAdapter;
	private final CastFolderNodeAdapter castFolderNodeAdapter;
	private final ProjectAdapter projectAdapter;
	private final TrashCanFolderRepository trashCanFolderRepository;

	public <T extends BaseFile<?>> TrashCan toEntity(T entity) {
		FolderType folderType = determineTableName(entity);
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findByItemIdAndTableName(entity.getParentId(), folderType)
			.orElseGet(() -> newTrashCanFolder(entity));
		return TrashCan.builder()
			.itemId(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.trashCanFolder(trashCanFolder)
			.build();
	}

	private <T extends BaseFile<?>> TrashCanFolder newTrashCanFolder(T entity){
		FolderType folderType = determineTableName(entity);
		BaseFolder baseFolder = getBaseFolderByRepository(folderType, entity.getParentId());
		Project project = projectAdapter.findById(baseFolder.getProjectId());
		TrashCanFolder trashCanFolder = TrashCanFolder.builder()
				.itemId(entity.getParentId())
				.tableName(folderType)
				.name(baseFolder.getName())
				.description(baseFolder.getDescription())
				.project(project)
				.trashCanList(null)
				.build();
		return trashCanFolderRepository.save(trashCanFolder);
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
			case UNIVERSE -> universeFolderAdapter.findById(parentId);
			case STORY -> storyFolderAdapter.findById(parentId);
			case CAST -> castFolderNodeAdapter.findById(parentId);
			default -> throw new IllegalArgumentException("todo Unsupported FolderType: " + folderType);
		};
	}
}
