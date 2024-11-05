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
import com.owing.api.trashcan.service.CreateTrashCanUserCase;
import com.owing.api.trashcan.service.DeleteTrashCanUserCase;
import com.owing.api.trashcan.service.ReadTrashCanUserCase;
import com.owing.api.trashcan.service.UpdateTrashCanUserCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trashcans")
@RequiredArgsConstructor
public class TrashCanController {

	private final CreateTrashCanUserCase createTrashCanUserCase;
	private final ReadTrashCanUserCase readTrashCanUserCase;
	private final UpdateTrashCanUserCase updateTrashCanUserCase;
	private final DeleteTrashCanUserCase deleteTrashCanUserCase;

	@GetMapping("")
	public ResponseEntity<TrashCanFolderResponse> getTrashCan(@RequestParam("projectId") Long projectId) {
		return ResponseEntity.ok(readTrashCanUserCase.execute(projectId));
	}

	@DeleteMapping("")
	public ResponseEntity<?> deleteAllTrashCan(@RequestParam("projectId") Long projectId) {
		deleteTrashCanUserCase.executeAll(projectId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{trashId}")
	public ResponseEntity<?> deleteTrashCan(@PathVariable Long trashId) {
		deleteTrashCanUserCase.execute(trashId);
		return ResponseEntity.noContent().build();
	}

	// todo 휴지통 요소 복원하기
	@PostMapping("/{trashId}")
	public ResponseEntity<?> restoreTrashCan(@PathVariable Long trashId) {
		updateTrashCanUserCase.executeRestore(trashId);
		return ResponseEntity.noContent().build();
	}

	// todo story 보여주기
	@GetMapping("/{trashId}/story")
	public ResponseEntity<?> getStoryTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok("story 구현이 되야 함");
	}

	// todo cast 보여주기
	@GetMapping("/{trashId}/cast")
	public ResponseEntity<?> getCastTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok("cast 구현이 되야 함");
	}

	// todo universe 보여주기
	@GetMapping("/{trashId}/universe")
	public ResponseEntity<?> getUniverseTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok("universe 구현이 되야 함");
	}

	// todo story-folder 보여주기
	@GetMapping("/{trashId}/story/folder")
	public ResponseEntity<?> getStoryFolderTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok("universe 구현이 되야 함");
	}

	// todo cast-folder 보여주기
	@GetMapping("/{trashId}/cast/folder")
	public ResponseEntity<?> getCastFolderTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok("universe 구현이 되야 함");
	}

	// todo universe-folder 보여주기
	@GetMapping("/{trashId}/universe/folder")
	public ResponseEntity<?> getUniverseFolderTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok("universe 구현이 되야 함");
	}

	@DeleteMapping("/{trashId}/folder")
	public ResponseEntity<?> deleteTrashCanFolder(@PathVariable Long trashId) {
		deleteTrashCanUserCase.executeFolder(trashId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{trashId}/folder")
	public ResponseEntity<?> restoreTrashCanFolder(@PathVariable Long trashId) {
		updateTrashCanUserCase.executeRestoreFolder(trashId);
		return ResponseEntity.noContent().build();
	}
}
