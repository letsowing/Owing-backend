package com.owing.api.story.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.controller.DndFileController;
import com.owing.api.dnd.service.DndFileCrudService;
import com.owing.api.story.model.dto.request.AddStoryContentRequest;
import com.owing.api.story.model.dto.request.StoryCrashRequest;
import com.owing.api.story.model.dto.request.UpdateStoryRequest;
import com.owing.api.story.model.dto.response.CrashCheckLogResponse;
import com.owing.api.story.model.dto.response.StorySpellCheckLogResponse;
import com.owing.api.story.service.CheckStoryCrashUseCase;
import com.owing.api.story.service.CheckStorySpellUseCase;
import com.owing.api.story.service.ReadStoryCrashLogUseCase;
import com.owing.api.story.service.ReadStorySpellLogUseCase;
import com.owing.api.story.service.UpdateStoryUseCase;
import com.owing.api.story.service.WriteStoryUseCase;
import com.owing.api.story.service.dnd.StoryCrudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/stories")
@RequiredArgsConstructor
@Tag(name="원고 /stories", description="원고 API")
public class StoryController extends DndFileController {
	private final WriteStoryUseCase createDndUseCase;
	private final StoryCrudService storyFileCrudService;
	private final CheckStoryCrashUseCase checkStoryCrashUseCase;
	private final CheckStorySpellUseCase checkStorySpellUseCase;
	private final ReadStorySpellLogUseCase readStorySpellLogUseCase;
	private final ReadStoryCrashLogUseCase readStoryCrashLogUseCase;
	private final UpdateStoryUseCase updateStoryUseCase;

	@PostMapping("/{storyId}")
	@Operation(summary = "✨일반: 원고 내용 작성", description = "원고 내용을 작성합니다. 생성 & 수정시 사용합니다.")
	public ResponseEntity<?> createStory(@PathVariable Long storyId, @RequestBody AddStoryContentRequest request) {
        createDndUseCase.execute(storyId, request);
		return ResponseEntity.status(HttpStatus.OK).build();
    }

	@PutMapping("/{storyId}")
	@Operation(summary = "✨일반: 원고 정보 수정", description = "원고 정보를 수정합니다.")
	public ResponseEntity<?> updateStory(@PathVariable Long storyId, @RequestBody @Valid UpdateStoryRequest request) {
		updateStoryUseCase.execute(storyId, request);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PostMapping("/{storyId}/crash-check")
	@Operation(summary = "✨AI: 설정 충돌 검사", description = "원고 설정 충돌을 검사합니다.")
	public ResponseEntity<CrashCheckLogResponse> checkStoryCrash(@PathVariable Long storyId, @RequestBody StoryCrashRequest request) throws Exception{
		return ResponseEntity.ok(checkStoryCrashUseCase.execute(storyId, request));
	}

	@GetMapping("/{storyId}/crash-check")
	@Operation(summary = "✨AI: 설정 충돌 검사", description = "원고 설정 충돌 로그를 조회합니다..")
	public ResponseEntity<List<CrashCheckLogResponse>> getStoryCrash(@PathVariable Long storyId) {
		return ResponseEntity.ok(readStoryCrashLogUseCase.execute(storyId));
	}

	@PostMapping("/{storyId}/spell-check")
	@Operation(summary = "✨AI: 맞춤법 검사", description = "맞춤법을 검사합니다. 사실 AI가 아님")
	public ResponseEntity<StorySpellCheckLogResponse> checkStorySpell(@PathVariable Long storyId) {
		return ResponseEntity.ok(checkStorySpellUseCase.execute(storyId));
	}

	@GetMapping("/{storyId}/spell-check")
	@Operation(summary = "✨AI: 맞춤법 검사", description = "맞춤법 검사 로그를 조회합니다.")
	public ResponseEntity<List<StorySpellCheckLogResponse>> getStorySpellLog(@PathVariable Long storyId) {
		return ResponseEntity.ok(readStorySpellLogUseCase.execute(storyId));
	}

	@Override
	protected DndFileCrudService dndCrudService() {
		return storyFileCrudService;
	}
}
