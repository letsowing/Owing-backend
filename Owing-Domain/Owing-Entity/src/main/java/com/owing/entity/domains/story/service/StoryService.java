package com.owing.entity.domains.story.service;

import com.owing.common.annotation.DomainService;
import com.owing.common.util.TextCounter;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryContent;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StoryService {
	private final StoryAdapter dndAdapter;

	public void updateContent(Story story, StoryContent storyContent) {
		story.createOrUpdateStoryText(storyContent);
		int textCnt = TextCounter.countText(storyContent.getContent(), false, true); // fixme
		story.updateTextCount(textCnt);
		dndAdapter.save(story);
	}

}
