package com.owing.api.dnd.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.owing.api.dnd.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.service.DndFolderCrudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

public abstract class BaseFolderController extends DndController<AddFolderRequest, UpdateFolderTitleRequest, UpdateFolderPositionRequest>{

	protected abstract DndFolderCrudService dndCrudService();

	@GetMapping
	@Operation(summary = "✨ DnD: 폴더 리스트 조회", description = "프로젝트 내 폴더 리스트와 밑에 딸린 파일 리스트를 조회합니다.")
	public ResponseEntity<?> getDndList(
		@Parameter(description = "폴더가 속한 프로젝트 ID") @RequestParam Long parentId) {
		return ResponseEntity.ok(dndCrudService().getList(parentId));
	}
}
