package com.owing.core.dnd.file.repository;

import com.owing.core.dnd.base.repository.DndRepository;
import com.owing.core.dnd.file.model.DndFile;

public interface DndFileRepository<T extends DndFile> extends DndRepository<T> {
    void incrementPositionAfter(Long targetPosition, Long projectId);
}
