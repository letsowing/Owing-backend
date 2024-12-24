package com.owing.entity.domains.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.DndRepository;
import com.owing.core.dnd.folder.adapter.DndFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryFolderAdapter extends DndFolderAdapter<StoryFolder> {

	private final StoryFolderRepository dndRepository;

	@Override
	protected DndRepository<StoryFolder> dndRepository() {
		return dndRepository;
	}

	public void restoreById(Long folderItemId) {
		dndRepository.restoreById(folderItemId);
	}
}
