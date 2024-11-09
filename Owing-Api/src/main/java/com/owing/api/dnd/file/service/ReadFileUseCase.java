package com.owing.api.dnd.file.service;

import java.util.List;

import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.file.model.dto.response.FileInfoListResponse;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.entity.dnd.base.service.BaseDndDomainService;
import com.owing.entity.dnd.file.model.BaseFile;
import com.owing.entity.dnd.folder.model.BaseFolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ReadFileUseCase<T extends BaseFile<F>, F extends BaseFolder> implements
	ReadDndUseCase<FileInfoResponse, FileInfoListResponse> {
	protected final BaseFileMapper<T, F> dndMapper;
	protected final BaseDndDomainService<T> baseDndDomainService;

	public FileInfoResponse executeRetrieve(Long dndId){
		T entity = baseDndDomainService.getEntity(dndId);
		return dndMapper.toInfoResponse(entity);
	}

	public FileInfoListResponse executeList(Long dndId) {
		List<T> entityList = baseDndDomainService.getEntityList(dndId);
		return dndMapper.toListResponse(entityList);
	}

}