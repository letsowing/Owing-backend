package com.owing.entity.domains.story.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.owing.core.dnd.service.shift.DndShiftAdapter;
import com.owing.core.dnd.service.shift.DndShiftService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StoryFolderDndService extends DndShiftService<StoryFolder> {
	private final StoryFolderAdapter dndAdapter;
	private final StoryAdapter storyAdapter;

	@Transactional("jpaTransactionManager")
	public void restore(Long folderItemId, List<Long> trashCanItemIds) {
		dndAdapter.restoreById(folderItemId);
		trashCanItemIds.forEach(storyAdapter::restoreById);
	}

	@Override
	protected DndShiftAdapter<StoryFolder> dndAdapter() {
		return dndAdapter;
	}
}
