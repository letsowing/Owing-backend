package com.owing.entity.dnd.file.repository;

import com.owing.entity.dnd.base.repository.BaseDndRepository;
import com.owing.entity.dnd.file.model.BaseFile;
import com.owing.entity.dnd.folder.model.BaseFolder;

public interface BaseFileRepository<T extends BaseFile<F>, F extends BaseFolder> extends BaseDndRepository<T> {
}
