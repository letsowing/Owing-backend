package com.owing.entity.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.entity.dnd.folder.service.BaseFolderDomainService;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

@DomainService
public class StoryFolderDomainService extends BaseFolderDomainService<StoryFolder> {
	public StoryFolderDomainService(
		StoryFolderRepository dndRepository,
		StoryFolderAdapter dndAdapter,
		StoryFolderShiftOrderingStrategy orderingStrategy) {
		super(dndRepository, dndAdapter, orderingStrategy);
	}
}
