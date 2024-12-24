package com.owing.api.universe.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.CreateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUniverseFolderUseCase extends CreateFolderUseCase<UniverseFolder> {

    private final MemberUtils memberUtils;
    private final DndDomainService<UniverseFolder> dndDomainService;
    private final BaseFolderMapper<UniverseFolder> dndMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndDomainService<UniverseFolder> baseDndDomainService() {
        return dndDomainService;
    }

    @Override
    protected BaseFolderMapper<UniverseFolder> dndMapper() {
        return dndMapper;
    }
}
