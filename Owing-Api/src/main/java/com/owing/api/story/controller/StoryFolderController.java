package com.owing.api.story.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.base.controller.BaseFolderController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderRequest;
import com.owing.api.story.service.folder.CreateStoryFolderUseCase;
import com.owing.api.story.service.folder.DeleteStoryFolderUseCase;
import com.owing.api.story.service.folder.ReadStoryFolderUseCase;
import com.owing.api.story.service.folder.UpdateStoryFolderUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stories/folders")
@RequiredArgsConstructor
public class StoryFolderController extends BaseFolderController {
	private final CreateStoryFolderUseCase createDndUseCase;
	private final ReadStoryFolderUseCase readDndUseCase;
	private final DeleteStoryFolderUseCase deleteDndUseCase;
	private final UpdateStoryFolderUseCase updateDndUseCase;

	@Override
	protected CreateDndUseCase<?, AddFolderRequest> createDndUseCase() {
		return createDndUseCase;
	}

	@Override
	protected ReadDndUseCase<?, ?> readDndUseCase() {
		return readDndUseCase;
	}

	@Override
	protected DeleteDndUseCase deleteDndUseCase() {
		return deleteDndUseCase;
	}

	@Override
	protected UpdateDndUseCase<?, UpdateFolderRequest, UpdateFolderPositionRequest> updateDndUseCase() {
		return updateDndUseCase;
	}
}
