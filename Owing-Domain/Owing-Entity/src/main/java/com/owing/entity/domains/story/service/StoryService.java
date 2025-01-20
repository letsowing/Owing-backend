package com.owing.entity.domains.story.service;

import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryContent;
import com.owing.entity.domains.story.model.dto.StoryVO;
import com.owing.entity.domains.story.textCount.TextCounter;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class StoryService extends DndService<Story> {

	private final StoryAdapter dndAdapter;
	private final StoryShiftOrderingStrategy orderingStrategy;

	@Override
	protected DndAdapter<Story> dndAdapter() {
		return dndAdapter;
	}

	@Override
	protected OrderingStrategy<Story> orderingStrategy() {
		return orderingStrategy;
	}

	public void updateContent(Story story, StoryContent storyContent) {
		story.createOrUpdateStoryText(storyContent);
		int textCnt = TextCounter.countText(storyContent.getContent(), false, true); // fixme
		story.updateTextCount(textCnt);
		dndAdapter.save(story);
	}

	public List<StoryVO> findAllByProjectId(Long projectId) {
		 return dndAdapter.findByProjectId(projectId).stream().map(s -> StoryVO.from(s, Jsoup.parse(s.getContent()).text())).toList();
	}

	@Transactional
	public void restoreById(Long itemId) {
		dndAdapter.restoreById(itemId);
	}

	@Transactional
	public Story update(Story oldStory, Story newStory) {
		oldStory.update(newStory);
		return oldStory;
	}

	public String getParsedContent(Story story) {
		return Jsoup.parse(story.getContent()).text();
	}
}
