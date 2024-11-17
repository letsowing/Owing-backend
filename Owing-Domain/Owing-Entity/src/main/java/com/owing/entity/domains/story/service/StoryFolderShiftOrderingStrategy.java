package com.owing.entity.domains.story.service;

import org.springframework.stereotype.Component;

import com.owing.core.dnd.base.orderStrategy.shift.FolderShiftOrderingStrategy;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

@Component
public class StoryFolderShiftOrderingStrategy extends FolderShiftOrderingStrategy<StoryFolder> {
	public StoryFolderShiftOrderingStrategy(
		StoryFolderAdapter dndAdapter,
		StoryFolderRepository dndRepository) {
		super(dndAdapter, dndRepository);
	}
}
