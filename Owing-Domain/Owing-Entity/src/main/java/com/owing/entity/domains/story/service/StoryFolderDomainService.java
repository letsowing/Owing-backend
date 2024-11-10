package com.owing.entity.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.service.BaseFolderDomainService;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StoryFolderDomainService extends BaseFolderDomainService<StoryFolder> {
	private final StoryFolderRepository dndRepository;
	private final StoryFolderAdapter dndAdapter;
	private final StoryFolderShiftOrderingStrategy orderingStrategy;

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
}
