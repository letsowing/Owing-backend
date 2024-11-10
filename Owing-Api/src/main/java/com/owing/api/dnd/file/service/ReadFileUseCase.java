package com.owing.api.dnd.file.service;

import java.util.List;

import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.file.model.dto.response.FileInfoListResponse;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class ReadFileUseCase<T extends BaseFile<F>, F extends BaseFolder> implements
	ReadDndUseCase<FileInfoResponse, FileInfoListResponse> {
	protected abstract BaseFileMapper<T, F> dndMapper();
	protected abstract BaseDndDomainService<T> baseDndDomainService();

	public FileInfoResponse executeRetrieve(Long dndId){
		T entity = baseDndDomainService().getEntity(dndId);
		return dndMapper().toInfoResponse(entity);
	}

	public FileInfoListResponse executeList(Long dndId) {
		List<T> entityList = baseDndDomainService().getEntityList(dndId);
		return dndMapper().toListResponse(entityList);
	}

}