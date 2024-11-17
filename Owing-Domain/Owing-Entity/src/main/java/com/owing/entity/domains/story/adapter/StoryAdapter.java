package com.owing.entity.domains.story.adapter;

import java.util.List;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.core.dnd.file.repository.BaseFileRepository;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.model.dto.StoryInfo;
import com.owing.entity.domains.story.repository.StoryDeletedRepository;
import com.owing.entity.domains.story.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryAdapter extends BaseFileAdapter<Story, StoryFolder> {
	private final StoryRepository storyRepository;
	private final StoryDeletedRepository storyDeletedRepository;

	@Override
	protected BaseFileRepository<Story, StoryFolder> dndRepository() {
		return storyRepository;
	}

	public List<Story> findByProjectId(Long projectId) {
		return storyRepository.findByFolder_ProjectId(projectId);
	}

    public StoryInfo findDeletedById(Long itemId) {
		return storyDeletedRepository.findDeletedById(itemId);
    }
}
