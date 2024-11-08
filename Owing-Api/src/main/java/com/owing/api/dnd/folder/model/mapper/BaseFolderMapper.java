package com.owing.api.dnd.folder.model.mapper;

import java.util.List;

import com.owing.api.dnd.base.model.mapper.DndMapper;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

public abstract class BaseFolderMapper<T extends BaseFolderEntity> implements
	DndMapper<T, AddFolderRequest, UpdateFolderRequest> {
	public FolderInfoResponse toInfoResponse(T entity) {
		return FolderInfoResponse.from(entity);
	}

	public FolderInfoListResponse toListResponse(List<T> dndList) {
		return new FolderInfoListResponse(dndList.stream()
			.map(FolderInfoResponse::from)
			.toList());
	}
}
