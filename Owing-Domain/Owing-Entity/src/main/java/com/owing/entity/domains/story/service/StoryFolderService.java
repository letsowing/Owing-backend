package com.owing.entity.domains.story.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StoryFolderService extends DndService<StoryFolder> {
	private final StoryFolderAdapter dndAdapter;
	private final StoryAdapter storyAdapter;
	private final StoryFolderShiftOrderingStrategy orderingStrategy;

	@Override
	protected DndAdapter<StoryFolder> dndAdapter() {
		return dndAdapter;
	}

	@Override
	protected OrderingStrategy<StoryFolder> orderingStrategy() {
		return orderingStrategy;
	}

	@Transactional("jpaTransactionManager")
	public void restore(Long folderItemId, List<Long> trashCanItemIds) {
		dndAdapter.restoreById(folderItemId);
		trashCanItemIds.forEach(storyAdapter::restoreById);
	}
}
