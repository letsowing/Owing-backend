package com.owing.api.story.controller;

import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.dto.response.StorySpellCheckLogResponse;
import com.owing.api.story.service.story.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owing.api.dnd.base.controller.BaseFileController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.story.model.dto.request.AddStoryContentRequest;
import com.owing.api.story.model.dto.request.StoryCrashRequest;
import com.owing.api.story.model.dto.request.UpdateStoryRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/v1/stories")
@RequiredArgsConstructor
@Tag(name="원고 /stories", description="원고 API")
public class StoryController extends BaseFileController {
	private final CreateStoryUseCase createDndUseCase;
	private final ReadStoryUseCase readDndUseCase;
	private final DeleteStoryUseCase deleteDndUseCase;
	private final UpdateStoryUseCase updateDndUseCase;
	private final CheckStoryCrashUseCase checkStoryCrashUseCase;
	private final CheckStorySpellUseCase checkStorySpellUseCase;
	private final ReadStorySpellLogUseCase readStorySpellLogUseCase;
	private final ReadStoryCrashLogUseCase readStoryCrashLogUseCase;

	@PostMapping("/{storyId}")
	@Operation(summary = "✨일반: 원고 내용 작성", description = "원고 내용을 작성합니다. 생성 & 수정시 사용합니다.")
	public ResponseEntity<?> createStory(@PathVariable Long storyId, @RequestBody AddStoryContentRequest request) {
        createDndUseCase.executeText(storyId, request);
		return ResponseEntity.status(HttpStatus.OK).build();
    }

	@PutMapping("/{storyId}")
	@Operation(summary = "✨일반: 원고 정보 수정", description = "원고 정보를 수정합니다.")
	public ResponseEntity<?> updateStory(@PathVariable Long storyId, @RequestBody UpdateStoryRequest request) {
		updateDndUseCase.execute(storyId, request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{storyId}/crash-check")
	@Operation(summary = "✨AI: 설정 충돌 검사", description = "원고 설정 충돌을 검사합니다.")
	public ResponseEntity<?> checkStoryCrash(@PathVariable Long storyId, @RequestBody StoryCrashRequest request) throws Exception{
		return ResponseEntity.ok(checkStoryCrashUseCase.execute(storyId, request));
	}

	@GetMapping("/{storyId}/crash-check")
	@Operation(summary = "✨AI: 설정 충돌 검사", description = "원고 설정 충돌 로그를 조회합니다..")
	public ResponseEntity<List<CrashCheckLogResponse>> getStoryCrash(@PathVariable Long storyId) {
		return ResponseEntity.ok(readStoryCrashLogUseCase.execute(storyId));
	}

	@PostMapping("/{storyId}/spell-check")
	@Operation(summary = "✨AI: 맞춤법 검사", description = "맞춤법을 검사합니다. 사실 AI가 아님")
	public ResponseEntity<?> checkStorySpell(@PathVariable Long storyId) {
		return ResponseEntity.ok(checkStorySpellUseCase.execute(storyId));
	}

	@GetMapping("/{storyId}/spell-check")
	@Operation(summary = "✨AI: 맞춤법 검사", description = "맞춤법 검사 로그를 조회합니다.")
	public ResponseEntity<List<StorySpellCheckLogResponse>> getStorySpellLog(@PathVariable Long storyId) {
		return ResponseEntity.ok(readStorySpellLogUseCase.execute(storyId));
	}

	@Override
	protected CreateDndUseCase<AddFileRequest> createDndUseCase() {
		return createDndUseCase;
	}

	@Override
	protected ReadDndUseCase readDndUseCase() {
		return readDndUseCase;
	}

	@Override
	protected DeleteDndUseCase deleteDndUseCase() {
		return deleteDndUseCase;
	}

	@Override
	protected UpdateDndUseCase<UpdateFileTitleRequest, UpdateFilePositionRequest> updateDndUseCase() {
		return updateDndUseCase;
	}
}
