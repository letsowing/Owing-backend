package com.owing.entity.domains.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.repository.StoryRepository;

@Adaptor
public class StoryAdapter extends BaseFileAdapter<Story> {

	public StoryAdapter(StoryRepository storyRepository) {
		super(storyRepository);
	}
}
