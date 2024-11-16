package com.owing.api.trashcan.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="쓰레기파일 /trashcans", description="쓰레기파일 API")
public class TrashCanController {

	private final CreateTrashCanUserCase createTrashCanUserCase;
	private final ReadTrashCanUserCase readTrashCanUserCase;
	private final UpdateTrashCanUserCase updateTrashCanUserCase;
	private final DeleteTrashCanUserCase deleteTrashCanUserCase;

	@DeleteMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기 영구 삭제", description = "쓰레기통에서 쓰레기를 영구 삭제합니다.")
	public ResponseEntity<?> deleteTrashCan(@PathVariable Long trashId) {
		deleteTrashCanUserCase.execute(trashId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{trashId}")
	@Operation(summary = "✨일반: 쓰레기 복원", description = "쓰래기를 복원합니다.")
	public ResponseEntity<?> restoreTrashCan(@PathVariable Long trashId) {
		updateTrashCanUserCase.executeRestore(trashId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{trashId}/story")
	@Operation(summary = "✨일반: 원고 쓰레기 상세보기", description = "원고 쓰레기를 상세 봅니다.")
	public ResponseEntity<?> getStoryTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanUserCase.executeStory(trashId));
	}

	@GetMapping("/{trashId}/cast")
	@Operation(summary = "✨일반: 인물 쓰레기 상세보기", description = "인물 쓰레기를 상세 봅니다.")
	public ResponseEntity<CastNode> getCastTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanUserCase.executeCast(trashId));
	}

	@GetMapping("/{trashId}/universe")
	@Operation(summary = "✨일반: 세계관 쓰레기 상세보기", description = "세계관 쓰레기를 상세 봅니다.")
	public ResponseEntity<Universe> getUniverseTrashCan(@PathVariable Long trashId) {
		return ResponseEntity.ok(readTrashCanUserCase.executeUniverse(trashId));
	}
}
