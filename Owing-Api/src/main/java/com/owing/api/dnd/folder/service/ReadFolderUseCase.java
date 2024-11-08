package com.owing.api.dnd.folder.service;

import java.util.List;

import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;
import com.owing.entity.dnd.base.service.DndDomainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ReadFolderUseCase<T extends BaseFolderEntity>  implements
	ReadDndUseCase<FolderInfoResponse, FolderInfoListResponse> {
	protected final BaseFolderMapper<T> dndMapper;
	protected final DndDomainService<T> dndDomainService;

	public FolderInfoResponse executeRetrieve(Long dndId){
		T entity = dndDomainService.getEntity(dndId);
		return dndMapper.toInfoResponse(entity);
	}

	public FolderInfoListResponse executeList(Long dndId) {
		List<T> entityList = dndDomainService.getEntityList(dndId);
		return dndMapper.toListResponse(entityList);
	}

}