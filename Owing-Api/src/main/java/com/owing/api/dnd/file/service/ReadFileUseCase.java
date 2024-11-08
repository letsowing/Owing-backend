package com.owing.api.dnd.file.service;

import java.util.List;

import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.file.model.dto.response.FileInfoListResponse;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;
import com.owing.entity.dnd.base.service.DndDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ReadFileUseCase<T extends BaseFileEntity<F>, F extends BaseFolderEntity> implements
	ReadDndUseCase<FileInfoResponse, FileInfoListResponse> {
	protected final BaseFileMapper<T, F> dndMapper;
	protected final DndDomainService<T> dndDomainService;

	public FileInfoResponse executeRetrieve(Long dndId){
		T entity = dndDomainService.getEntity(dndId);
		return dndMapper.toInfoResponse(entity);
	}

	public FileInfoListResponse executeList(Long dndId) {
		List<T> entityList = dndDomainService.getEntityList(dndId);
		return dndMapper.toListResponse(entityList);
	}

}