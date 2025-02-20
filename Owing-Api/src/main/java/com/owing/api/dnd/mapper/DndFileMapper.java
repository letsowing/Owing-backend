package com.owing.api.dnd.mapper;

import com.owing.api.dnd.dto.request.AddFileRequest;
import com.owing.api.dnd.dto.response.FileInfoResponse;
import com.owing.core.dnd.model.DndFile;
import com.owing.core.dnd.model.DndFolder;

public interface DndFileMapper<T extends DndFile, F extends DndFolder> {
	T toEntity(AddFileRequest dto, F folder);
	FileInfoResponse toInfoResponse(T entity);
}
