package com.owing.api.dnd.file.model.mapper;

import java.util.List;

import com.owing.api.dnd.base.model.mapper.DndMapper;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;
import com.owing.api.dnd.file.model.dto.response.FileInfoListResponse;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.entity.dnd.file.model.BaseFileEntity;
import com.owing.entity.dnd.folder.model.BaseFolderEntity;

public abstract class BaseFileMapper<T extends BaseFileEntity<F>, F extends BaseFolderEntity> implements
	DndMapper<T, AddFileRequest, UpdateFileRequest> {

	public abstract T toEntity(AddFileRequest addDndRequest, F folder);

	public FileInfoResponse toInfoResponse(T entity) {
		return FileInfoResponse.from(entity);
	}

	public FileInfoListResponse toListResponse(List<T> dndList) {
		return new FileInfoListResponse(dndList.stream()
			.map(FileInfoResponse::from)
			.toList());
	}
}
