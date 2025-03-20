package com.owing.api.story.service.dnd;

import org.springframework.stereotype.Service;

import com.owing.api.dnd.mapper.DndFolderMapper;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.api.story.model.mapper.StoryFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.adapter.DndAdapter;
import com.owing.core.dnd.service.DndService;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderDndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryFolderCrudService extends DndFolderCrudService<StoryFolder> {
	private final MemberUtils memberUtils;
	private final StoryFolderMapper storyFolderMapper;
	private final StoryFolderAdapter storyFolderAdapter;
	private final StoryFolderDndService storyFolderDndService;
	private final TrashCanDomainService trashCanDomainService;


	@Override
	protected DndService<StoryFolder> dndService() {
		return storyFolderDndService;
	}

	@Override
	protected DndFolderMapper<StoryFolder> folderMapper() {
		return storyFolderMapper;
	}

	@Override
	protected DndAdapter<StoryFolder> folderAdapter() {
		return storyFolderAdapter;
	}

	@Override
	protected TrashCanDomainService trashCanDomainService() {
		return trashCanDomainService;
	}

}
