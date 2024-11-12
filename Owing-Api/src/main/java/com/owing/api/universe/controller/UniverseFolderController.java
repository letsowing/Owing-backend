package com.owing.api.universe.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owing.api.dnd.base.controller.BaseFolderController;
import com.owing.api.dnd.base.service.CreateDndUseCase;
import com.owing.api.dnd.base.service.DeleteDndUseCase;
import com.owing.api.dnd.base.service.UpdateDndUseCase;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.api.universe.service.folder.CreateUniverseFolderUseCase;
import com.owing.api.universe.service.folder.DeleteUniverseFolderUseCase;
import com.owing.api.universe.service.folder.ReadUniverseFolderUseCase;
import com.owing.api.universe.service.folder.UpdateUniverseFolderUseCase;
import com.owing.entity.domains.universe.model.UniverseFolder;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/universes/folders")
@RequiredArgsConstructor
@Tag(name="세계관 폴더 /universes/folders", description="세계관 폴더 API")
public class UniverseFolderController extends BaseFolderController {
	private final CreateUniverseFolderUseCase createUniverseUseCase;
	private final UpdateUniverseFolderUseCase updateUniverseUseCase;
	private final ReadUniverseFolderUseCase readUniverseUseCase;
	private final DeleteUniverseFolderUseCase deleteUniverseUseCase;

	@Override
	protected CreateDndUseCase<AddFolderRequest> createDndUseCase() {
		return createUniverseUseCase;
	}

	@Override
	protected ReadFolderUseCase<UniverseFolder> readDndUseCase() {
		return readUniverseUseCase;
	}

	@Override
	protected DeleteDndUseCase deleteDndUseCase() {
		return deleteUniverseUseCase;
	}

	@Override
	protected UpdateDndUseCase<UpdateFolderTitleRequest, UpdateFolderPositionRequest> updateDndUseCase() {
		return updateUniverseUseCase;
	}
}
