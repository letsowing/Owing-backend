package com.owing.api.dnd.file.model.mapper;

import com.owing.api.dnd.base.model.mapper.DndMapper;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileTitleRequest;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFileMapper<T extends BaseFile<F>, F extends BaseFolder> implements DndMapper<T> {
	public abstract T toEntity(UpdateFileTitleRequest dto);
	public abstract T toEntity(AddFileRequest dto, F Folder);
}
