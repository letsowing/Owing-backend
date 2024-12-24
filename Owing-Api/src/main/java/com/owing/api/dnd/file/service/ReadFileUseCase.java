package com.owing.api.dnd.file.service;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.folder.model.DndFolder;

public abstract class ReadFileUseCase<T extends DndFile, F extends DndFolder> implements ReadDndUseCase {
	protected abstract BaseFileMapper<T, F> dndMapper();
	protected abstract DndDomainService<T> baseDndDomainService();

	public DndInfoResponse executeRetrieve(Long dndId){
		T entity = baseDndDomainService().getEntity(dndId);
		return dndMapper().toInfoResponse(entity);
	}

}