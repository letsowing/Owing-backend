package com.owing.api.dnd.file.model.mapper;

import com.owing.api.dnd.base.model.mapper.DndMapper;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.folder.model.DndFolder;

public abstract class BaseFileMapper<T extends DndFile, F extends DndFolder> implements DndMapper<T> {
	public abstract T toEntity(UpdateFileTitleRequest dto);
	public abstract T toEntity(AddFileRequest dto, F Folder);
}
