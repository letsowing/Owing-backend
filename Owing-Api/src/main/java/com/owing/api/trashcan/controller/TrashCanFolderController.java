package com.owing.api.trashcan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.trashcan.model.dto.response.TrashCanFolderResponse;
import com.owing.api.trashcan.service.folder.CreateTrashCanFolderUserCase;
import com.owing.api.trashcan.service.folder.DeleteTrashCanFolderUserCase;
import com.owing.api.trashcan.service.folder.ReadTrashCanFolderUserCase;
import com.owing.api.trashcan.service.folder.UpdateTrashCanFolderUserCase;
import com.owing.entity.domains.trashcan.model.TrashCanFolder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trashcans/folders")
@RequiredArgsConstructor
@Tag(name="쓰레기_폴더 /trashcans/folders", description="쓰레기_폴더 API")
public class TrashCanFolderController {
	private final CreateTrashCanFolderUserCase createTrashCanFolderUserCase;
	private final ReadTrashCanFolderUserCase readTrashCanFolderUserCase;
	private final UpdateTrashCanFolderUserCase updateTrashCanFolderUserCase;
	private final DeleteTrashCanFolderUserCase deleteTrashCanFolderUserCase;

	@GetMapping
	@Operation(summary = "✨일반: 쓰레기_폴더 모두보기", description = "프로젝트에 해당하는 쓰레기 리스트를 모두 확인합니다.")
	public ResponseEntity<TrashCanFolderResponse> getTrashCanFolder(@RequestParam("projectId") Long projectId) {
		return ResponseEntity.ok(readTrashCanFolderUserCase.executeProject(projectId));
	}

	@DeleteMapping
	@Operation(summary = "✨일반: 쓰레기_폴더 모두 삭제하기", description = "프로젝트에 해당하는 모든 쓰레기를 영구 삭제합니다.")
	public ResponseEntity<?> deleteAllTrashCan(@RequestParam("projectId") Long projectId) {
		deleteTrashCanFolderUserCase.executeAll(projectId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기_폴더 상세보기", description = "쓰레기_폴더를 상세하게 봅니다.")
	public ResponseEntity<TrashCanFolder> getFolderTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanFolderUserCase.execute(trashId));
	}

	@DeleteMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기_폴더 영구 삭제", description = "쓰레기_폴더를 영구적으로 삭제합니다.")
	public ResponseEntity<?> deleteTrashCanFolder(@PathVariable Long trashId) {
		deleteTrashCanFolderUserCase.executeFolder(trashId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기_폴더 복원하기", description = "쓰레기_폴더를 복원합니다.")
	public ResponseEntity<?> restoreTrashCanFolder(@PathVariable Long trashId) {
		updateTrashCanFolderUserCase.executeRestoreFolder(trashId);
		return ResponseEntity.noContent().build();
	}
}
