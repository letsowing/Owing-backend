package com.owing.api.story.service.dnd;

import org.springframework.stereotype.Service;

import com.owing.api.dnd.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.api.story.model.mapper.StoryFolderMapper;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.story.adapter.StoryFolderAdapter;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.story.service.StoryFolderService;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryFolderCrudCrudService extends DndFolderCrudService<StoryFolder> {

	private final MemberUtils memberUtils;
	private final StoryFolderService storyFolderService;
	private final StoryFolderMapper storyFolderMapper;
	private final TrashCanFolderDomainService trashCanFolderDomainService;
	private final ProjectAdapter projectAdapter;
	private final TrashCanFolderMapper trashCanFolderMapper;
	private final StoryFolderAdapter storyFolderAdapter;
	@Override
	protected MemberUtils memberUtils() {
		return memberUtils;
	}

	@Override
	protected DndService<StoryFolder> folderService() {
		return storyFolderService;
	}

	@Override
	protected BaseFolderMapper<StoryFolder> folderMapper() {
		return storyFolderMapper;
	}

	@Override
	protected DndAdapter<StoryFolder> folderAdapter() {
		return storyFolderAdapter;
	}

	@Override
	protected TrashCanFolderDomainService trashCanFolderDomainService() { return this.trashCanFolderDomainService; }

	@Override
	protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

	@Override
	protected TrashCanFolderMapper trashCanFolderMapper() { return this.trashCanFolderMapper; }
}
