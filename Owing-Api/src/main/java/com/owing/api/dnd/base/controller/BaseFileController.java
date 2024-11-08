package com.owing.api.dnd.base.controller;

import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.api.dnd.file.service.ReadFileUseCase;
import com.owing.api.dnd.file.service.UpdateFileUseCase;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

public abstract class BaseFileController<T extends BaseFileEntity<F>, F extends BaseFolderEntity> extends DndController<AddFileRequest, UpdateFileRequest, UpdateFilePositionRequest>{

	public BaseFileController(
		CreateFileUseCase<T, F> createDndUseCase,
		ReadFileUseCase<T, F> readDndUseCase,
		DeleteFileUseCase<T, F> deleteDndUseCase,
		UpdateFileUseCase<T, F> updateDndUseCase) {
		super(createDndUseCase, readDndUseCase, deleteDndUseCase, updateDndUseCase);
	}
}
