package com.owing.api.story.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.base.controller.BaseFileController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;
import com.owing.api.story.service.story.CreateStoryUseCase;
import com.owing.api.story.service.story.DeleteStoryUseCase;
import com.owing.api.story.service.story.ReadStoryUseCase;
import com.owing.api.story.service.story.UpdateStoryUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController extends BaseFileController {
	private final CreateStoryUseCase createDndUseCase;
	private final ReadStoryUseCase readDndUseCase;
	private final DeleteStoryUseCase deleteDndUseCase;
	private final UpdateStoryUseCase updateDndUseCase;


	@Override
	protected CreateDndUseCase<?, AddFileRequest> createDndUseCase() {
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
	protected UpdateDndUseCase<UpdateFileRequest, UpdateFilePositionRequest> updateDndUseCase() {
		return updateDndUseCase;
	}
}
