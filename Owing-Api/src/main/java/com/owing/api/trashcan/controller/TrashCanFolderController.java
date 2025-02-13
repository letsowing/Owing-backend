package com.owing.api.trashcan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.model.dto.response.TrashCanFolderListResponse;
import com.owing.api.trashcan.service.folder.DeleteTrashCanFolderUseCase;
import com.owing.api.trashcan.service.folder.ReadTrashCanFolderUseCase;
import com.owing.api.trashcan.service.folder.UpdateTrashCanFolderUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trashcans/folders")
@RequiredArgsConstructor
@Tag(name="쓰레기_폴더 /trashcans/folders", description="쓰레기_폴더 API")
public class TrashCanFolderController {
	private final ReadTrashCanFolderUseCase readTrashCanFolderUseCase;
	private final UpdateTrashCanFolderUseCase updateTrashCanFolderUseCase;
	private final DeleteTrashCanFolderUseCase deleteTrashCanFolderUseCase;

	@GetMapping
	@Operation(summary = "✨일반: 쓰레기_폴더 모두보기", description = "프로젝트에 해당하는 쓰레기 리스트를 모두 확인합니다.")
	public ResponseEntity<TrashCanFolderListResponse> getTrashCanFolder(@RequestParam("projectId") Long projectId) {
		return ResponseEntity.ok(readTrashCanFolderUseCase.executeProject(projectId));
	}

	@DeleteMapping
	@Operation(summary = "✨일반: 쓰레기_폴더 모두 삭제하기", description = "프로젝트에 해당하는 모든 쓰레기를 영구 삭제합니다.")
	public ResponseEntity<?> deleteAllTrashCan(@RequestParam("projectId") Long projectId) {
		deleteTrashCanFolderUseCase.executeAll(projectId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기_폴더 상세보기", description = "쓰레기_폴더를 상세하게 봅니다.")
	public ResponseEntity<TrashCanFolderResponse> getFolderTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanFolderUseCase.execute(trashId));
	}

	@DeleteMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기_폴더 영구 삭제", description = "쓰레기_폴더를 영구적으로 삭제합니다.")
	public ResponseEntity<?> deleteTrashCanFolder(@PathVariable Long trashId) {
		deleteTrashCanFolderUseCase.executeFolder(trashId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기_폴더 복원하기", description = "쓰레기_폴더를 복원합니다.")
	public ResponseEntity<?> restoreTrashCanFolder(@PathVariable Long trashId) {
		updateTrashCanFolderUseCase.executeRestoreFolder(trashId);
		return ResponseEntity.noContent().build();
	}
}
