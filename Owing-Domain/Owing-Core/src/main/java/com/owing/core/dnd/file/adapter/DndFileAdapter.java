package com.owing.core.dnd.file.adapter;

import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.file.model.DndFile;
import com.owing.core.dnd.file.repository.DndFileRepository;
import com.owing.core.dnd.folder.model.DndFolder;

public abstract class DndFileAdapter<T extends DndFile> extends DndAdapter<T> {

    @Override
    protected abstract DndFileRepository<T> dndRepository();

    public void incrementPositionAfter(long targetPosition, Long projectId) {
        dndRepository().incrementPositionAfter(targetPosition, projectId);
    }

}
