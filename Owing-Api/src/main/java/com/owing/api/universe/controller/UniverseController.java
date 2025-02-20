package com.owing.api.universe.controller;

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
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.service.CreateUniversePresignedUrlUseCase;
import com.owing.api.universe.service.GenerateUniverseImageUseCase;
import com.owing.api.universe.service.dnd.UniverseCrudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/universes")
@RequiredArgsConstructor
@Tag(name="세계관 /universes", description="세계관 API")
public class UniverseController extends DndFileController {

	private final UniverseCrudService universeCrudService;
	private final CreateUniversePresignedUrlUseCase createUniversePresignedUrlUseCase;
	private final GenerateUniverseImageUseCase generateUniverseImageUseCase;

	/* 세계관 생성 */
	@PostMapping
	@Operation(summary = "✨일반: 세계관 생성", description = "세계관을 생성합니다.")
	public ResponseEntity<UniverseShortInfoResponse> createUniverse(@Valid @RequestBody AddUniverseRequest addUniverseRequest) {
		return ResponseEntity.ok(universeCrudService.create(addUniverseRequest));
	}

	/* 세계관 수정 */
	@PutMapping("/{universeId}")
	@Operation(summary = "✨일반: 세계관 수정", description = "세계관을 수정합니다.")
	public ResponseEntity<UniverseShortInfoResponse> updateUniverse(
		@PathVariable Long universeId,
		@Valid @RequestBody UpdateUniverseRequest updateUniverseRequest) {
		return ResponseEntity.ok(universeCrudService.update(universeId, updateUniverseRequest));
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
	public ResponseEntity<UniverseImageResponse> generateUniverseImage(@Valid @RequestBody GenerateUniverseImageRequest generateUniverseImageRequest) {
		return ResponseEntity.ok(generateUniverseImageUseCase.execute(generateUniverseImageRequest));
	}

	@Override
	protected DndFileCrudService dndCrudService() {
		return universeCrudService;
	}
}
