package com.owing.api.dnd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.owing.api.dnd.dto.common.request.CommonAddFileRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFileNameRequest;
import com.owing.api.dnd.dto.common.request.CommonUpdateFilePositionRequest;
import com.owing.api.dnd.service.DndFileCrudService;

import io.swagger.v3.oas.annotations.Operation;

public abstract class DndFileController {
	protected abstract DndFileCrudService dndCrudService();

	@GetMapping("/{id}")
	@Operation(summary = "✨ 일반: 파일 or 폴더 상세 조회", description = "파일 or 폴더 상세조회")
	public ResponseEntity<?> getDnd(@PathVariable Long id) {
		return ResponseEntity.ok(dndCrudService().get(id));
	}

	@PostMapping("/dnd")
	@Operation(summary = "✨ DnD: 파일 or 폴더 생성", description = "폴더탭에서 파일이나 폴더를 생성합니다. DnD 생성은 Title만 받습니다.")
	public ResponseEntity<?> createDnd(@RequestBody CommonAddFileRequest addDndRequest) {
		return ResponseEntity.ok(dndCrudService().create(addDndRequest));
	}

	@PatchMapping("/{id}/title")
	@Operation(summary = "✨ DnD: 파일 or 폴더 이름 수정", description = "폴더탭에서 파일이나 폴더의 이름을 변경합니다.")
	public ResponseEntity<Void> updateDndName(@PathVariable Long id, @RequestBody CommonUpdateFileNameRequest updateDndRequest) {
		dndCrudService().updateName(id, updateDndRequest);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/position")
	@Operation(summary = "✨ DnD: 파일 or 폴더 위치 이동", description = "폴더탭에서 파일이나 폴더의 위치를 이동합니다.")
	public ResponseEntity<Void> updateDndPosition(@PathVariable Long id, @RequestBody CommonUpdateFilePositionRequest updateDndPositionRequest) {
		dndCrudService().updatePosition(id, updateDndPositionRequest);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "✨ DnD: 파일 or 폴더 삭제", description = "폴더탭에서 파일이나 폴더를 삭제합니다.")
	public ResponseEntity<Void> deleteDnd(@PathVariable Long id) {
		dndCrudService().delete(id);
		return ResponseEntity.noContent().build();
	}
}
