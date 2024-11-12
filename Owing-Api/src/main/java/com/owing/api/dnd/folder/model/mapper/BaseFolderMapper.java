package com.owing.api.dnd.folder.model.mapper;

import java.util.List;

import com.owing.api.dnd.base.model.mapper.DndMapper;
import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoListResponse;
import com.owing.api.dnd.folder.model.dto.response.FolderInfoResponse;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFolderMapper<T extends BaseFolder> implements DndMapper<T> {
	public abstract T toEntity(AddFolderRequest addDndRequest);
	public abstract T toEntity(UpdateFolderTitleRequest updateDndRequest);

	public FolderInfoResponse toInfoResponse(T entity) {
		return FolderInfoResponse.from(entity);
	}

	public FolderInfoListResponse toListResponse(List<T> dndList) {
		return new FolderInfoListResponse(
				dndList.stream()
						.map(this::toInfoResponse)
						.toList()
		);
	}
}
