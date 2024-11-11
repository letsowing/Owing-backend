package com.owing.api.dnd.folder.service;

import java.util.List;

import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class ReadFolderUseCase<T extends BaseFolder>  implements
	ReadDndUseCase<FolderInfoResponse, FolderInfoListResponse> {
	protected abstract BaseFolderMapper<T> dndMapper();
	protected abstract BaseDndDomainService<T> baseDndDomainService();

	public FolderInfoResponse executeRetrieve(Long dndId){
		T entity = baseDndDomainService().getEntity(dndId);
		return dndMapper().toInfoResponse(entity);
	}

	public FolderInfoListResponse executeList(Long dndId) {
		List<T> entityList = baseDndDomainService().getEntityList(dndId);
		return dndMapper().toListResponse(entityList);
	}

}