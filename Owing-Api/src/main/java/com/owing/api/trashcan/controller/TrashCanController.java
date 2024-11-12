package com.owing.api.trashcan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.trashcan.service.trashcan.CreateTrashCanUserCase;
import com.owing.api.trashcan.service.trashcan.DeleteTrashCanUserCase;
import com.owing.api.trashcan.service.trashcan.ReadTrashCanUserCase;
import com.owing.api.trashcan.service.trashcan.UpdateTrashCanUserCase;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.universe.model.Universe;
import com.owing.node.domains.cast.model.CastNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/trashcans")
@RequiredArgsConstructor
public class TrashCanController {

	private final CreateTrashCanUserCase createTrashCanUserCase;
	private final ReadTrashCanUserCase readTrashCanUserCase;
	private final UpdateTrashCanUserCase updateTrashCanUserCase;
	private final DeleteTrashCanUserCase deleteTrashCanUserCase;

	@DeleteMapping("/{trashId}")
	public ResponseEntity<?> deleteTrashCan(@PathVariable Long trashId) {
		deleteTrashCanUserCase.execute(trashId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{trashId}")
	public ResponseEntity<?> restoreTrashCan(@PathVariable Long trashId) {
		updateTrashCanUserCase.executeRestore(trashId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{trashId}/story")
	public ResponseEntity<Story> getStoryTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanUserCase.executeStory(trashId));
	}

	@GetMapping("/{trashId}/cast")
	public ResponseEntity<CastNode> getCastTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanUserCase.executeCast(trashId));
	}

	@GetMapping("/{trashId}/universe")
	public ResponseEntity<Universe> getUniverseTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanUserCase.executeUniverse(trashId));
	}
}
