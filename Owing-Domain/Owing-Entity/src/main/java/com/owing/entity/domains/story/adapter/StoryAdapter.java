package com.owing.entity.domains.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.entity.dnd.file.adapter.BaseFileAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.repository.StoryRepositoryBase;

@Adaptor
public class StoryAdapter extends BaseFileAdapter<Story> {

	public StoryAdapter(StoryRepositoryBase storyRepository) {
		super(storyRepository);
	}
}
