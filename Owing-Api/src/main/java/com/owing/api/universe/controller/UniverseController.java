package com.owing.api.universe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.file.service.CreatePresignedUrlUseCase;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.GenerateUniverseImageRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseImageResponse;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
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
public class UniverseController {

	private final CreateUniverseUseCase createUniverseUseCase;
	private final UpdateUniverseUseCase updateUniverseUseCase;
	private final ReadUniverseUseCase readUniverseUseCase;
	private final DeleteUniverseUseCase deleteUniverseUseCase;
	private final CreatePresignedUrlUseCase createPresignedUrlUseCase;
	private final GenerateUniverseImageUseCase generateUniverseImageUseCase;

	/* 세계관 생성 */
	@PostMapping
	@Operation(summary = "✨ 세계관 생성", description = "세계관을 생성합니다.")
	public ResponseEntity<UniverseShortInfoResponse> createUniverse(@RequestBody AddUniverseRequest addUniverseRequest) {
		return ResponseEntity.ok(createUniverseUseCase.execute(addUniverseRequest));
	}

	/* 세계관 수정 */
	@PutMapping("/{universeId}")
	@Operation(summary = "✨ 세계관 수정", description = "세계관을 수정합니다.")
	public ResponseEntity<UniverseShortInfoResponse> updateUniverse(
		@PathVariable Long universeId,
		@RequestBody UpdateUniverseRequest updateUniverseRequest) {
		return ResponseEntity.ok(updateUniverseUseCase.execute(universeId, updateUniverseRequest));
	}

	@PatchMapping("/{universeId}")
	@Operation(summary = "✨ DnD: 파일 or 폴더 위치 이동", description = "폴더탭에서 파일이나 폴더의 위치를 이동합니다.")
	public ResponseEntity<Void> updateUniversePosition(
		@PathVariable Long universeId,
		@RequestBody UpdateFilePositionRequest updateUniverseRequest
	) {
		updateUniverseUseCase.executeUpdatePosition(universeId, updateUniverseRequest);
		return ResponseEntity.noContent().build();
	}

	/* 세계관 조회 */
	@GetMapping("/{universeId}")
	@Operation(summary = "✨ 일반: 파일 or 폴더 상세 조회", description = "파일 or 폴더 상세조회")
	public ResponseEntity<UniverseShortInfoResponse> readUniverse(@PathVariable Long universeId) {
		return ResponseEntity.ok(readUniverseUseCase.execute(universeId));
	}

	/* 세계관 삭제 */
	@DeleteMapping("/{universeId}")
	@Operation(summary = "✨ DnD: 파일 or 폴더 삭제", description = "폴더탭에서 파일이나 폴더를 삭제합니다.")
	public ResponseEntity<String> deleteUniverse(@PathVariable Long universeId) {
		deleteUniverseUseCase.execute(universeId);
		return ResponseEntity.noContent().build();
	}

	/* presigned url 생성 */
	@GetMapping("/files/{filename}")
	@Operation(summary = "✨ 일반: 세계관 presignedUrl", description = "presigned url 생성합니다.")
	public ResponseEntity<?> getFile(@PathVariable(value = "filename") String fileName) {
		return ResponseEntity.ok(createPresignedUrlUseCase.execute(fileName));
	}

	/* OpenAI - 세계관 이미지 생성 요청 후 S3 업로드 */
	@PostMapping("/images")
	public ResponseEntity<UniverseImageResponse> generateUniverseImage(@RequestBody GenerateUniverseImageRequest generateUniverseImageRequest) {
		return ResponseEntity.ok(generateUniverseImageUseCase.execute(generateUniverseImageRequest));
	}

}
