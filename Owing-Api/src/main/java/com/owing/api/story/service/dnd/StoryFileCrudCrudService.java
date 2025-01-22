package com.owing.api.story.service.dnd;

import org.springframework.stereotype.Service;

import com.owing.api.dnd.model.mapper.BaseFileMapper;
import com.owing.api.dnd.service.DndFileCrudService;
import com.owing.api.story.model.mapper.StoryMapper;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.story.adapter.StoryAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryFileCrudCrudService extends DndFileCrudService<Story, StoryFolder> {
	private final MemberUtils memberUtils;
	private final StoryService storyService;
	private final StoryMapper storyMapper;
	private final StoryFolderAdapter folderAdapter;
	private final StoryAdapter storyAdapter;
	private final TrashCanDomainService trashCanDomainService;
	private final TrashCanMapper trashCanMapper;


	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected DndService fileService() {
		return storyService;
	}

	@Override
	protected DndAdapter fileAdapter() {
		return storyAdapter;
	}

	@Override
	protected BaseFileMapper fileMapper() {
		return storyMapper;
	}

	@Override
	protected DndAdapter folderAdapter() {
		return folderAdapter;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}

	@Override
	protected TrashCanMapper trashCanMapper() {
		return trashCanMapper;
	}
}
