package com.owing.api.trashcan.service.folder;

import java.util.List;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.service.StoryFolderService;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;
import com.owing.entity.domains.universe.service.UniverseFolderService;
import com.owing.node.folder.cast.service.CastFolderNodeService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanFolderUserCase {
	private final CastFolderNodeService castFolderNodeDomainService;
	private final StoryFolderService storyFolderDomainService;
	private final UniverseFolderService universeFolderDomainService;
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;

	public void executeRestoreFolder(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findById(trashId);

		List<Long> trashCanItemIds = trashCanFolder.getTrashCanItemIds();

		switch (trashCanFolder.getTableName()) {
			case CAST -> castFolderNodeDomainService.restore(trashCanFolder.getItemId(), trashCanItemIds);
			case STORY -> storyFolderDomainService.restore(trashCanFolder.getItemId(), trashCanItemIds);
			case UNIVERSE -> universeFolderDomainService.restore(trashCanFolder.getItemId(), trashCanItemIds);
		}

		trashCanFolderAdaptor.deleteTrashCanFolderById(trashId);
	}
}
