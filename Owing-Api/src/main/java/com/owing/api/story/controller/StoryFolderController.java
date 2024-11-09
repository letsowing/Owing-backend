package com.owing.api.story.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.base.controller.BaseFolderController;
import com.owing.api.dnd.folder.service.CreateFolderUseCase;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.api.story.service.folder.DeleteStoryFolderUseCase;
import com.owing.api.story.service.folder.UpdateStoryFolderUseCase;
import com.owing.entity.domains.story.model.StoryFolder;

@RestController
@RequestMapping("/stories/folders")
public class StoryFolderController extends BaseFolderController<StoryFolder> {
	public StoryFolderController(
		CreateFolderUseCase<StoryFolder> createDndUseCase,
		ReadFolderUseCase<StoryFolder> readDndUseCase,
		DeleteStoryFolderUseCase deleteDndUseCase,
		UpdateStoryFolderUseCase updateDndUseCase) {
		super(createDndUseCase, readDndUseCase, deleteDndUseCase, updateDndUseCase);
	}
}
