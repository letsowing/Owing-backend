package com.owing.api.universe.service.folder;

import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadUniverseFolderUseCase extends ReadFolderUseCase<UniverseFolder> {
    private final BaseFolderMapper<UniverseFolder> dndMapper;
    private final DndDomainService<UniverseFolder> dndDomainService;

    @Override
    protected BaseFolderMapper<UniverseFolder> dndMapper() {
        return dndMapper;
    }

    @Override
    protected DndDomainService<UniverseFolder> baseDndDomainService() {
        return dndDomainService;
    }
}
