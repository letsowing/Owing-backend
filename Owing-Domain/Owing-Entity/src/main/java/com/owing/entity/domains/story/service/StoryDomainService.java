package com.owing.entity.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.service.BaseFileDomainService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StoryDomainService extends BaseFileDomainService<Story, StoryFolder> {

	private final StoryRepository dndRepository;
	private final StoryAdapter dndAdapter;
	private final StoryShiftOrderingStrategy orderingStrategy;

	@Override
	protected BaseDndRepository<Story> dndRepository() {
		return dndRepository;
	}

	@Override
	protected BaseDndAdapter<Story> dndEntityAdapter() {
		return dndAdapter;
	}

	@Override
	protected OrderingStrategy<Story> orderingStrategy() {
		return orderingStrategy;
	}
}
