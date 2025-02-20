package com.owing.api.universe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.controller.DndFolderController;
import com.owing.api.dnd.service.DndFolderCrudService;
import com.owing.api.universe.service.dnd.UniverseFolderCrudService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/universes/folders")
@RequiredArgsConstructor
@Tag(name="세계관 폴더 /universes/folders", description="세계관 폴더 API")
public class UniverseFolderController extends DndFolderController {

	private final UniverseFolderCrudService universeFolderCrudService;

	@Override
	protected DndFolderCrudService dndCrudService() {
		return universeFolderCrudService;
	}
}
