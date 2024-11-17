package com.owing.entity.domains.story.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.repository.StoryFolderRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class StoryFolderAdapter extends BaseFolderAdapter<StoryFolder> {

	private final StoryFolderRepository dndRepository;

	@Override
	protected BaseDndRepository<StoryFolder> dndRepository() {
		return dndRepository;
	}
}
