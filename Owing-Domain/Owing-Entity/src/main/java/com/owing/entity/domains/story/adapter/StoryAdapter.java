package com.owing.entity.domains.story.adapter;

import java.util.List;

import org.jsoup.Jsoup;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.file.adapter.DndFileAdapter;
import com.owing.core.dnd.file.repository.DndFileRepository;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.dto.StoryInfo;
import com.owing.entity.domains.story.repository.StoryDeletedRepository;
import com.owing.entity.domains.story.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryAdapter extends DndFileAdapter<Story> {
	private final StoryRepository storyRepository;
	private final StoryDeletedRepository storyDeletedRepository;

	@Override
	protected DndFileRepository<Story> dndRepository() {
		return storyRepository;
	}

	public List<Story> findByProjectId(Long projectId) {
		return storyRepository.findByFolder_ProjectId(projectId);
	}

    public StoryInfo findDeletedById(Long itemId) {
		StoryInfo storyInfo =  storyDeletedRepository.findDeletedById(itemId);

		String content = null;

		if(storyInfo.content() != null) {
			content = Jsoup.parse(storyInfo.content()).text();
		}

		return StoryInfo.builder()
			.storyId(storyInfo.storyId())
			.name(storyInfo.name())
			.description(storyInfo.description())
			.textCount(storyInfo.textCount())
			.content(content)
			.build();
    }

	public void restoreById(Long itemId) {
		storyRepository.restoreById(itemId);
	}
}
