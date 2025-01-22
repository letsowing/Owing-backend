package com.owing.api.story.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.controller.BaseFolderController;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.api.story.service.dnd.StoryFolderCrudCrudService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/stories/folders")
@RequiredArgsConstructor
@Tag(name="원고 폴더 /stories/folders", description="원고 폴더 API")
public class StoryFolderController extends BaseFolderController {

	private final StoryFolderCrudCrudService storyFolderCrudService;

	@Override
	protected DndFolderCrudService dndCrudService() {
		return storyFolderCrudService;
	}
}
