package com.owing.api.universe.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.UpdateFolderUseCase;
import com.owing.api.universe.model.mapper.UniverseFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateUniverseFolderUseCase extends UpdateFolderUseCase<UniverseFolder> {
    private final MemberUtils memberUtils;
    private final UniverseFolderDomainService baseDndDomainService;
    private final UniverseFolderMapper dndMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndDomainService<UniverseFolder> baseDndDomainService() {
        return baseDndDomainService;
    }

    @Override
    protected BaseFolderMapper<UniverseFolder> dndMapper() {
        return dndMapper;
    }
}
