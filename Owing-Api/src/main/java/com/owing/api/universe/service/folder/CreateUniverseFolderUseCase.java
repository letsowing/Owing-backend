package com.owing.api.universe.service.folder;

import com.owing.api.universe.model.mapper.UniverseFolderMapper;
import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.CreateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.universe.service.UniverseFolderService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateUniverseFolderUseCase extends CreateFolderUseCase<UniverseFolder> {

    private final MemberUtils memberUtils;
    private final UniverseFolderService universeFolderService;
    private final UniverseFolderMapper universeFolderMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndService<UniverseFolder> folderService() {
        return universeFolderService;
    }

    @Override
    protected BaseFolderMapper<UniverseFolder> folderMapper() {
        return universeFolderMapper;
    }
}
