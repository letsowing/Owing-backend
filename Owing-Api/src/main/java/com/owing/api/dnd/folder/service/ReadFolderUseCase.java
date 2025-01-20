package com.owing.api.dnd.folder.service;

import java.util.List;

import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.model.DndFolder;

public abstract class ReadFolderUseCase<T extends DndFolder>  implements
	ReadDndUseCase {
	protected abstract DndAdapter<T> folderAdapter();
	protected abstract BaseFolderMapper<T> folderMapper();

	public FolderInfoResponse executeRetrieve(Long folderId){
		T entity = folderAdapter().findById(folderId);
		return folderMapper().toInfoResponse(entity);
	}

	public FolderInfoListResponse executeList(Long projectId) {
		List<T> entityList = folderAdapter().findAllByParentId(projectId);
		return folderMapper().toListResponse(entityList);
	}

}