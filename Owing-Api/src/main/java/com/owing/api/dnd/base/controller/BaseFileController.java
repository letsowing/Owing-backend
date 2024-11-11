package com.owing.api.dnd.base.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public abstract class BaseFileController extends DndController<AddFileRequest, UpdateFileRequest, UpdateFilePositionRequest>{
	@GetMapping
	@Operation(summary = "✨ DnD: 파일 리스트 조회", description = "파일 or 폴더 리스트 조회")
	public ResponseEntity<?> getDndList(
		@Parameter(description = "파일 또는 폴더의 고유 ID") @RequestParam Long parentId) {
		return ResponseEntity.ok(readDndUseCase().executeList(parentId));
	}
}
