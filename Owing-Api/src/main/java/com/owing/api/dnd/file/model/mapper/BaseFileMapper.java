package com.owing.api.dnd.file.model.mapper;

import java.util.List;

import com.owing.api.dnd.base.model.mapper.DndMapper;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;
import com.owing.api.dnd.file.model.dto.response.FileInfoListResponse;
import com.owing.api.dnd.file.model.dto.response.FileInfoResponse;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFileMapper<T extends BaseFile<F>, F extends BaseFolder> implements
	DndMapper<T, AddFileRequest, UpdateFileRequest> {

	public abstract T toEntity(AddFileRequest addDndRequest, F folder);

	public FileInfoResponse toInfoResponse(T entity) {
		return FileInfoResponse.from(entity);
	}

	public FileInfoListResponse toListResponse(List<T> dndList) {
		return new FileInfoListResponse(
				dndList.stream()
						.map(this::toInfoResponse)
						.toList()
		);
	}
}
