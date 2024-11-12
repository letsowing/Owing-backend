package com.owing.entity.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.service.BaseFileDomainService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.model.StoryText;
import com.owing.entity.domains.story.repository.StoryRepository;
import com.owing.entity.domains.story.textCount.TextCounter;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StoryDomainService extends BaseFileDomainService<Story, StoryFolder> {

	private final StoryRepository dndRepository;
	private final StoryAdapter dndAdapter;
	private final StoryShiftOrderingStrategy orderingStrategy;
	private final StoryRepository storyRepository;

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

	public void updateContent(Story story, StoryText storyText) {
		story.createOrUpdateStoryText(storyText);
		int textCnt = TextCounter.countText(storyText.getContent(), false, true);
		story.updateTextCount(textCnt);
		storyRepository.save(story);
	}

	public void restoreById(Long itemId) {
		storyRepository.restoreById(itemId);
	}
}
