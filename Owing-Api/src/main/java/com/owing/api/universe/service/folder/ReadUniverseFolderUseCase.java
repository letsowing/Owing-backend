package com.owing.api.universe.service.folder;

import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.api.universe.model.mapper.UniverseFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadUniverseFolderUseCase extends ReadFolderUseCase<UniverseFolder> {
    private final UniverseFolderMapper universeFolderMapper;
    private final UniverseFolderAdapter universeFolderAdapter;

    @Override
    protected DndAdapter<UniverseFolder> folderAdapter() {
        return universeFolderAdapter;
    }

    @Override
    protected BaseFolderMapper<UniverseFolder> folderMapper() {
        return universeFolderMapper;
    }
}
