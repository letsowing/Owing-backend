package com.owing.entity.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.dnd.file.service.BaseFileDomainService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryRepositoryBase;

@DomainService
public class StoryDomainService extends BaseFileDomainService<Story, StoryFolder> {

	public StoryDomainService(StoryRepositoryBase dndRepository,
		StoryAdapter dndAdapter,
		StoryShiftOrderingStrategy orderingStrategy) {
		super(dndRepository, dndAdapter, orderingStrategy);
	}
}
