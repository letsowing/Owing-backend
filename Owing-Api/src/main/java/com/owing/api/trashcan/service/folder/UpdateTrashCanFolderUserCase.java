package com.owing.api.trashcan.service.folder;

import java.util.List;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.service.StoryFolderDomainService;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.service.UniverseFolderDomainService;
import com.owing.entity.folders.trashcan.model.FolderType;
import com.owing.entity.folders.trashcan.service.TrashCanFolderDomainService;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanFolderUserCase {
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final CastFolderNodeDomainService castFolderNodeDomainService;
	private final StoryFolderDomainService storyFolderDomainService;
	private final UniverseFolderDomainService universeFolderDomainService;

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
