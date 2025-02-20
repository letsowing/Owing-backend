package com.owing.api.story.service.dnd;

import org.springframework.stereotype.Service;

import com.owing.api.dnd.mapper.DndFileMapper;
import com.owing.api.dnd.service.DndFileCrudService;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryDndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryCrudService extends DndFileCrudService<Story, StoryFolder> {
	private final MemberUtils memberUtils;
	private final StoryMapper storyMapper;
	private final StoryFolderAdapter folderAdapter;
	private final StoryAdapter storyAdapter;
	private final TrashCanDomainService trashCanDomainService;
	private final StoryDndService storyDndService;

	@Override
	protected DndService<Story> dndService() {
		return storyDndService;
	}

	@Override
	protected DndAdapter<Story> fileAdapter() {
		return storyAdapter;
	}

	@Override
	protected DndAdapter<StoryFolder> folderAdapter() {
		return folderAdapter;
	}

	@Override
	protected DndFileMapper<Story, StoryFolder> fileMapper() {
		return storyMapper;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}
}
