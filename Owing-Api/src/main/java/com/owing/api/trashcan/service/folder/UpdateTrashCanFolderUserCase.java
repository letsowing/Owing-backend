package com.owing.api.trashcan.service.folder;

import java.util.List;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.service.StoryFolderService;
import com.owing.entity.domains.universe.service.UniverseFolderService;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;
import com.owing.node.folder.cast.service.CastFolderNodeService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanFolderUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final CastFolderNodeService castFolderNodeDomainService;
	private final StoryFolderService storyFolderDomainService;
	private final UniverseFolderService universeFolderDomainService;

	public void executeRestoreFolder(Long trashId) {
		FolderType tableName = trashCanFolderDomainService.findTableNameById(trashId);
		Long folderItemId = trashCanFolderDomainService.findItemIdById(trashId);
		List<Long> trashCanItemIds = trashCanFolderDomainService.findTrashCanIdsById(trashId);

		if(tableName.isCast()){
			castFolderNodeDomainService.restore(folderItemId, trashCanItemIds);
		} else if (tableName.isStory()){
			storyFolderDomainService.restore(folderItemId, trashCanItemIds);
		} else if (tableName.isUniverse()){
			universeFolderDomainService.restore(folderItemId, trashCanItemIds);
		}

		trashCanFolderDomainService.deleteTrashCanFolderById(trashId);
	}
}
