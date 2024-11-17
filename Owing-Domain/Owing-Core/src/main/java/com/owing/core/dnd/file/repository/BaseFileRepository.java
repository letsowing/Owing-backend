package com.owing.core.dnd.file.repository;

import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.folder.model.BaseFolder;

public interface BaseFileRepository<T extends BaseFile<F>, F extends BaseFolder> extends BaseDndRepository<T> {
    void incrementPositionAfter(Long targetPosition, Long projectId);
}
