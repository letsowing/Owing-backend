package com.owing.api.dnd.mapper;

import java.util.List;

import com.owing.api.dnd.dto.request.AddFolderRequest;
import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.dto.response.FolderInfoResponse;
import com.owing.core.dnd.model.DndFolder;

public interface DndFolderMapper<T extends DndFolder> {
	FolderInfoResponse toInfoResponse(T entity);
	FolderInfoListResponse toListResponse(List<T> entity);
	T toEntity(AddFolderRequest d);
}
