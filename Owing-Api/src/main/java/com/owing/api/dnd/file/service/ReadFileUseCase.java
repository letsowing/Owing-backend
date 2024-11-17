package com.owing.api.dnd.file.service;

import com.owing.api.dnd.base.model.dto.response.DndInfoResponse;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class ReadFileUseCase<T extends BaseFile<F>, F extends BaseFolder> implements ReadDndUseCase {
	protected abstract BaseFileMapper<T, F> dndMapper();
	protected abstract BaseDndDomainService<T> baseDndDomainService();

	public DndInfoResponse executeRetrieve(Long dndId){
		T entity = baseDndDomainService().getEntity(dndId);
		return dndMapper().toInfoResponse(entity);
	}

}