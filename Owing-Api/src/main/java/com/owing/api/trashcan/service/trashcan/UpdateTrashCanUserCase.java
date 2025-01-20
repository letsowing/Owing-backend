package com.owing.api.trashcan.service.trashcan;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.service.StoryService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.entity.domains.universe.service.UniverseService;
import com.owing.entity.domains.trashcan.model.FolderType;
import com.owing.node.domains.cast.service.CastNodeService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanUserCase {
	private final TrashCanDomainService trashCanDomainService;
	private final CastNodeService castNodeDomainService;
	private final StoryService storyDomainService;
	private final UniverseService universeDomainService;

	public void executeRestore(Long trashId) {
		FolderType tableName = trashCanDomainService.findTableNameById(trashId);
		Long itemId = trashCanDomainService.findItemId(trashId);
		if (tableName.isCast()){
			castNodeDomainService.restoreById(itemId);
		} else if (tableName.isStory()){
			storyDomainService.restoreById(itemId);
		} else if (tableName.isUniverse()){
			universeDomainService.restoreById(itemId);
		}
		trashCanDomainService.deleteTrashCan(trashId);
	}

}
