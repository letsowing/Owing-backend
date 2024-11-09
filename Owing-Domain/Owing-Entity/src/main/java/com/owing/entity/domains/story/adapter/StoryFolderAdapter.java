package com.owing.entity.domains.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

@Adaptor
public class StoryFolderAdapter extends BaseFolderAdapter<StoryFolder> {

	public StoryFolderAdapter(StoryFolderRepository dndRepository) {
		super(dndRepository);
	}
}
