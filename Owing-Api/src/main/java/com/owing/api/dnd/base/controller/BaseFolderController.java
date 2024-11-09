package com.owing.api.dnd.base.controller;

import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderRequest;
import com.owing.api.dnd.folder.service.CreateFolderUseCase;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.api.dnd.folder.service.UpdateFolderUseCase;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFolderController<T extends BaseFolder> extends DndController<AddFolderRequest, UpdateFolderRequest, UpdateFolderPositionRequest>{

	public BaseFolderController(
		CreateFolderUseCase<T> createDndUseCase,
		ReadFolderUseCase<T> readDndUseCase,
		DeleteFolderUseCase<T> deleteDndUseCase,
		UpdateFolderUseCase<T> updateDndUseCase) {
		super(createDndUseCase, readDndUseCase, deleteDndUseCase, updateDndUseCase);
	}
}
