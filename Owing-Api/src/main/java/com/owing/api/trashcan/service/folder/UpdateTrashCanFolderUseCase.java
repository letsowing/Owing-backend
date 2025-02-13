package com.owing.api.trashcan.service.folder;

import java.util.List;

import com.owing.common.annotation.UseCase;
import com.owing.entity.domains.story.service.StoryFolderDndService;
import com.owing.entity.domains.trashcan.adaptor.TrashCanFolderAdaptor;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;
import com.owing.entity.domains.universe.service.UniverseFolderDndService;
import com.owing.node.folder.cast.service.CastFolderDndService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateTrashCanFolderUseCase {
	private final TrashCanFolderAdaptor trashCanFolderAdaptor;
	private final StoryFolderDndService storyFolderDndService;
	private final UniverseFolderDndService universeFolderDndService;
	private final CastFolderDndService castFolderDndService;

	public void executeRestoreFolder(Long trashId) {
		TrashCanFolder trashCanFolder = trashCanFolderAdaptor.findById(trashId);

		List<Long> trashCanItemIds = trashCanFolder.getTrashCanItemIds();

		switch (trashCanFolder.getTableName()) {
			case CAST -> castFolderDndService.restore(trashCanFolder.getItemId(), trashCanItemIds);
			case STORY -> storyFolderDndService.restore(trashCanFolder.getItemId(), trashCanItemIds);
			case UNIVERSE -> universeFolderDndService.restore(trashCanFolder.getItemId(), trashCanItemIds);
		}

		trashCanFolderAdaptor.deleteTrashCanFolderById(trashId);
	}
}
