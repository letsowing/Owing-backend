package com.owing.entity.domains.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.service.shift.adapter.FolderShiftAdapter;
import com.owing.core.dnd.service.shift.repository.DndShiftRepository;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryFolderAdapter extends FolderShiftAdapter<StoryFolder> {

	private final StoryFolderRepository dndRepository;

	@Override
	protected DndShiftRepository<StoryFolder> dndRepository() {
		return dndRepository;
	}

	public void restoreById(Long folderItemId) {
		dndRepository.restoreById(folderItemId);
	}
}
