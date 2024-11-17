package com.owing.api.universe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.base.controller.BaseFileController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.api.file.service.CreatePresignedUrlUseCase;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.service.universe.CreateUniversePresignedUrlUseCase;
import com.owing.api.universe.service.universe.CreateUniverseUseCase;
import com.owing.api.universe.service.universe.DeleteUniverseUseCase;
import com.owing.api.universe.service.universe.GenerateUniverseImageUseCase;
import com.owing.api.universe.service.universe.ReadUniverseUseCase;
import com.owing.api.universe.service.universe.UpdateUniverseUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/universes")
@RequiredArgsConstructor
@Tag(name="세계관 /universes", description="세계관 API")
public class UniverseController extends BaseFileController {

	private final CreateUniverseUseCase createUniverseUseCase;
	private final UpdateUniverseUseCase updateUniverseUseCase;
	private final ReadUniverseUseCase readUniverseUseCase;
	private final DeleteUniverseUseCase deleteUniverseUseCase;
	private final CreateUniversePresignedUrlUseCase createUniversePresignedUrlUseCase;
	private final GenerateUniverseImageUseCase generateUniverseImageUseCase;

	/* 세계관 생성 */
	@PostMapping
	@Operation(summary = "✨일반: 세계관 생성", description = "세계관을 생성합니다.")
	public ResponseEntity<UniverseShortInfoResponse> createUniverse(@RequestBody AddUniverseRequest addUniverseRequest) {
		return ResponseEntity.ok(createUniverseUseCase.execute(addUniverseRequest));
	}

	/* 세계관 수정 */
	@PutMapping("/{universeId}")
	@Operation(summary = "✨일반: 세계관 수정", description = "세계관을 수정합니다.")
	public ResponseEntity<UniverseShortInfoResponse> updateUniverse(
		@PathVariable Long universeId,
		@RequestBody UpdateUniverseRequest updateUniverseRequest) {
		return ResponseEntity.ok(updateUniverseUseCase.execute(universeId, updateUniverseRequest));
	}

	/* presigned url 생성 */
	@GetMapping("/files/{fileExtension}")
	@Operation(summary = "✨ 일반: 세계관 presignedUrl", description = "presigned url 생성합니다.")
	public ResponseEntity<?> getFile(@PathVariable(value = "fileExtension") String fileExtension) {
		return ResponseEntity.ok(createUniversePresignedUrlUseCase.execute(fileExtension));
	}

	/* OpenAI - 세계관 이미지 생성 요청 후 S3 업로드 */
	@PostMapping("/images")
	@Operation(summary = "✨ OpenAI: 세계관 이미지 생성 요청 후 S3 업로드", description = "세계관 이미지 생성 요청 후 S3 업로드")
	public ResponseEntity<UniverseImageResponse> generateUniverseImage(@RequestBody GenerateUniverseImageRequest generateUniverseImageRequest) {
		return ResponseEntity.ok(generateUniverseImageUseCase.execute(generateUniverseImageRequest));
	}

	@Override
	protected CreateDndUseCase<AddFileRequest> createDndUseCase() {
		return createUniverseUseCase;
	}

	@Override
	protected ReadDndUseCase readDndUseCase() {
		return readUniverseUseCase;
	}

	@Override
	protected DeleteDndUseCase deleteDndUseCase() {
		return deleteUniverseUseCase;
	}

	@Override
	protected UpdateDndUseCase<UpdateFileTitleRequest, UpdateFilePositionRequest> updateDndUseCase() {
		return updateUniverseUseCase;
	}
}
