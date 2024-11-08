package com.owing.api.story.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.base.controller.BaseFileController;
import com.owing.api.story.service.story.CreateStoryUseCase;
import com.owing.api.story.service.story.DeleteStoryUseCase;
import com.owing.api.story.service.story.ReadStoryUseCase;
import com.owing.api.story.service.story.UpdateStoryUseCase;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@RestController
@RequestMapping("/stories")
public class StoryController extends BaseFileController<Story, StoryFolder> {
	public StoryController(
		CreateStoryUseCase createDndUseCase,
		ReadStoryUseCase readDndUseCase,
		DeleteStoryUseCase deleteDndUseCase,
		UpdateStoryUseCase updateDndUseCase) {
		super(createDndUseCase, readDndUseCase, deleteDndUseCase, updateDndUseCase);
	}

}
