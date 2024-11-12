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
import com.owing.api.trashcan.service.folder.CreateTrashCanFolderUserCase;
import com.owing.api.trashcan.service.folder.DeleteTrashCanFolderUserCase;
import com.owing.api.trashcan.service.folder.ReadTrashCanFolderUserCase;
import com.owing.api.trashcan.service.folder.UpdateTrashCanFolderUserCase;
import com.owing.entity.folders.trashcan.model.TrashCanFolder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trashcans/folders")
@RequiredArgsConstructor
public class TrashCanFolderController {
	private final CreateTrashCanFolderUserCase createTrashCanFolderUserCase;
	private final ReadTrashCanFolderUserCase readTrashCanFolderUserCase;
	private final UpdateTrashCanFolderUserCase updateTrashCanFolderUserCase;
	private final DeleteTrashCanFolderUserCase deleteTrashCanFolderUserCase;

	@GetMapping
	public ResponseEntity<TrashCanFolderResponse> getTrashCan(@RequestParam("projectId") Long projectId) {
		return ResponseEntity.ok(readTrashCanFolderUserCase.executeProject(projectId));
	}

	@DeleteMapping
	public ResponseEntity<?> deleteAllTrashCan(@RequestParam("projectId") Long projectId) {
		deleteTrashCanFolderUserCase.executeAll(projectId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{trashId}")
	public ResponseEntity<TrashCanFolder> getFolderTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanFolderUserCase.execute(trashId));
	}

	@DeleteMapping("/{trashId}")
	public ResponseEntity<?> deleteTrashCanFolder(@PathVariable Long trashId) {
		deleteTrashCanFolderUserCase.executeFolder(trashId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{trashId}")
	public ResponseEntity<?> restoreTrashCanFolder(@PathVariable Long trashId) {
		updateTrashCanFolderUserCase.executeRestoreFolder(trashId);
		return ResponseEntity.noContent().build();
	}
}
