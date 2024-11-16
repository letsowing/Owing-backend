package com.owing.core.dnd.file.adapter;

import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.file.model.BaseFile;
import com.owing.core.dnd.file.repository.BaseFileRepository;
import com.owing.core.dnd.folder.model.BaseFolder;

public abstract class BaseFileAdapter<T extends BaseFile<F>, F extends BaseFolder> extends BaseDndAdapter<T> {

    @Override
    protected abstract BaseFileRepository<T, F> dndRepository();

    public void incrementPositionAfter(long targetPosition, Long projectId) {
        dndRepository().incrementPositionAfter(targetPosition, projectId);
    }

}
