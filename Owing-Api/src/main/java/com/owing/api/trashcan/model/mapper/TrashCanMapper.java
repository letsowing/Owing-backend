package com.owing.api.trashcan.model.mapper;

import com.owing.common.annotation.Mapper;
import com.owing.core.dnd.base.model.DndFile;
import com.owing.core.dnd.base.model.DndFolder;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.project.model.Project;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.trashcan.error.TrashCanErrorCode;
import com.owing.entity.domains.trashcan.error.exception.TrashCanException;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.universe.adapter.UniverseAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;
import com.owing.entity.domains.trashcan.repository.TrashCanFolderRepository;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
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
	private final UniverseAdapter universeAdapter;
	private final CastNodeAdapter castNodeAdapter;
	private final TrashCanFolderRepository trashCanFolderRepository;

	public <T extends DndFile> TrashCan toEntity(T entity) {
		FolderType folderType = determineTableName(entity);
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findByItemIdAndTableName(entity.getParentId(), folderType)
			.orElseGet(() -> newTrashCanFolder(entity));

		String imageUrl = GetImageUrl(folderType, entity.getId());

		return TrashCan.builder()
			.itemId(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.trashCanFolder(trashCanFolder)
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

	private <T extends DndFile> TrashCanFolder newTrashCanFolder(T entity){
		FolderType folderType = determineTableName(entity);
		DndFolder dndFolder = getBaseFolderByRepository(folderType, entity.getParentId());
		Project project = projectAdapter.findById(dndFolder.getProjectId());
		TrashCanFolder trashCanFolder = TrashCanFolder.builder()
				.itemId(entity.getParentId())
				.tableName(folderType)
				.name(dndFolder.getName())
				.description(dndFolder.getDescription())
				.project(project)
				.trashCanList(null)
				.build();
		return trashCanFolderRepository.save(trashCanFolder);
	}

	private <T extends DndFile> FolderType determineTableName(T entity) {
		String className = entity.getClass().getSimpleName().toLowerCase();

		if (className.contains("story")) {
			return FolderType.STORY;
		} else if (className.contains("cast")) {
			return FolderType.CAST;
		} else if (className.contains("universe")) {
			return FolderType.UNIVERSE;
		}

		throw TrashCanException.of(TrashCanErrorCode.TRASH_CAN_PARENT_NOT_FOUND);
	}

	private DndFolder getBaseFolderByRepository(FolderType folderType, Long parentId) {
		return switch (folderType) {
			case UNIVERSE -> universeFolderAdapter.findById(parentId);
			case STORY -> storyFolderAdapter.findById(parentId);
			case CAST -> castFolderNodeAdapter.findById(parentId);
		};
	}
}
