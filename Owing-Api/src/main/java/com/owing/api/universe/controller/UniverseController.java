package com.owing.api.universe.controller;

import org.springframework.http.HttpStatus;
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
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.file.service.CreatePresignedUrlUseCase;
import com.owing.api.universe.model.dto.request.AddUniverseRequest;
import com.owing.api.universe.model.dto.request.UpdateUniverseRequest;
import com.owing.api.universe.model.dto.response.UniverseShortInfoResponse;
import com.owing.api.universe.service.universe.CreateUniverseUseCase;
import com.owing.api.universe.service.universe.DeleteUniverseUseCase;
import com.owing.api.universe.service.universe.ReadUniverseUseCase;
import com.owing.api.universe.service.universe.UpdateUniverseUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/universes")
@RequiredArgsConstructor
public class UniverseController {

	private final CreateUniverseUseCase createUniverseUseCase;
	private final UpdateUniverseUseCase updateUniverseUseCase;
	private final ReadUniverseUseCase readUniverseUseCase;
	private final DeleteUniverseUseCase deleteUniverseUseCase;
	private final CreatePresignedUrlUseCase createPresignedUrlUseCase;

	/* 세계관 생성 */
	@PostMapping
	public ResponseEntity<UniverseShortInfoResponse> createUniverse(@RequestBody AddUniverseRequest addUniverseRequest) {
		return ResponseEntity.ok(createUniverseUseCase.execute(addUniverseRequest));
	}

	/* 세계관 수정 */
	@PutMapping("/{universeId}")
	public ResponseEntity<UniverseShortInfoResponse> updateUniverse(
		@PathVariable Long universeId,
		@RequestBody UpdateUniverseRequest updateUniverseRequest) {
		return ResponseEntity.ok(updateUniverseUseCase.execute(universeId, updateUniverseRequest));
	}

	@PatchMapping("/{universeId}")
	public ResponseEntity<FileInfoResponse> updateUniversePosition(
		@PathVariable Long universeId,
		@RequestBody UpdateFilePositionRequest updateUniverseRequest) {
		return ResponseEntity.ok(updateUniverseUseCase.executeUpdatePosition(universeId, updateUniverseRequest));
	}

	/* 세계관 조회 */
	@GetMapping("/{universeId}")
	public ResponseEntity<UniverseShortInfoResponse> readUniverse(@PathVariable Long universeId) {
		return ResponseEntity.ok(readUniverseUseCase.execute(universeId));
	}

	/* 세계관 삭제 */
	@DeleteMapping("/{universeId}")
	public ResponseEntity<String> deleteUniverse(@PathVariable Long universeId) {
		deleteUniverseUseCase.execute(universeId);
		return ResponseEntity.ok("삭제되었습니다.");
	}

	/* presigned url 생성 */
	@GetMapping("/files/{filename}")
	public ResponseEntity<?> getFile(@PathVariable(value = "filename") String fileName) {
		return ResponseEntity.ok(createPresignedUrlUseCase.execute(fileName));
	}

}
