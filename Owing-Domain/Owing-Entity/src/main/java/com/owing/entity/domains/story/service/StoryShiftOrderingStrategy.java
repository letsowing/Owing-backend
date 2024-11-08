package com.owing.entity.domains.story.service;

import org.springframework.stereotype.Component;

import com.owing.entity.dnd.base.orderStrategy.shift.FileShiftOrderingStrategy;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryRepositoryBase;

@Component
public class StoryShiftOrderingStrategy extends FileShiftOrderingStrategy<Story, StoryFolder> {
	public StoryShiftOrderingStrategy(StoryAdapter dndAdapter,
		StoryRepositoryBase dndRepository) {
		super(dndAdapter, dndRepository);
	}
}
