package com.owing.entity.domains.story.service;

import java.util.List;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.service.BaseFolderDomainService;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;
import com.owing.entity.domains.story.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StoryFolderDomainService extends BaseFolderDomainService<StoryFolder> {
	private final StoryFolderRepository dndRepository;
	private final StoryFolderAdapter dndAdapter;
	private final StoryFolderShiftOrderingStrategy orderingStrategy;
	private final StoryFolderRepository storyFolderRepository;
	private final StoryRepository storyRepository;

	@Override
	protected BaseDndRepository<StoryFolder> dndRepository() {
		return dndRepository;
	}

	@Override
	protected BaseDndAdapter<StoryFolder> dndEntityAdapter() {
		return dndAdapter;
	}

	@Override
	protected OrderingStrategy<StoryFolder> orderingStrategy() {
		return orderingStrategy;
	}

	public void restore(Long folderItemId, List<Long> trashCanItemIds) {
		storyFolderRepository.restoreById(folderItemId);
		for (Long id : trashCanItemIds) {
			storyRepository.restoreById(id);
		}
	}
}
