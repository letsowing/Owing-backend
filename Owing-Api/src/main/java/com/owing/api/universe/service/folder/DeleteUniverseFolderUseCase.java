package com.owing.api.universe.service.folder;

import com.owing.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.universe.adapter.UniverseFolderAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;
import com.owing.entity.domains.universe.service.UniverseFolderService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteUniverseFolderUseCase extends DeleteFolderUseCase<UniverseFolder> {

    private final MemberUtils memberUtils;
    private final UniverseFolderService universeFolderService;
    private final TrashCanFolderDomainService trashCanFolderDomainService;
    private final ProjectAdapter projectAdapter;
    private final TrashCanFolderMapper trashCanFolderMapper;
    private final UniverseFolderAdapter universeFolderAdapter;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndService<UniverseFolder> folderService() {
        return universeFolderService;
    }

    @Override
    protected DndAdapter<UniverseFolder> folderAdapter() {
        return universeFolderAdapter;
    }

    @Override
    protected TrashCanFolderDomainService trashCanFolderDomainService() { return this.trashCanFolderDomainService; }

    @Override
    protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

    @Override
    protected TrashCanFolderMapper trashCanFolderMapper() { return this.trashCanFolderMapper; }
}
