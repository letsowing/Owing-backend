package com.owing.api.dnd.file.service;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.DndFile;
import com.owing.core.dnd.base.model.DndFolder;

public abstract class ReadFileUseCase<T extends DndFile, F extends DndFolder> implements ReadDndUseCase {
	protected abstract BaseFileMapper<T, F> fileMapper();
	protected abstract DndAdapter<T> fileAdapter();

	public DndInfoResponse executeRetrieve(Long fileId){
		T entity = fileAdapter().findById(fileId);
		return fileMapper().toInfoResponse(entity);
	}

}