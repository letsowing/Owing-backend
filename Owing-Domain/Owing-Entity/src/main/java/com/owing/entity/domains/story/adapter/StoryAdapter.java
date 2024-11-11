package com.owing.entity.domains.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.repository.StoryRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryAdapter extends BaseFileAdapter<Story> {
	private final StoryRepository storyRepository;

	@Override
	protected BaseDndRepository<Story> dndRepository() {
		return storyRepository;
	}
}
