package com.owing.api.universe.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanFolderMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.project.adapter.ProjectAdapter;
import com.owing.entity.domains.universe.model.UniverseFolder;
import com.owing.entity.domains.trashcan.service.TrashCanFolderDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteUniverseFolderUseCase extends DeleteFolderUseCase<UniverseFolder> {

    private final MemberUtils memberUtils;
    private final DndDomainService<UniverseFolder> dndDomainService;
    private final TrashCanFolderDomainService trashCanFolderDomainService;
    private final ProjectAdapter projectAdapter;
    private final TrashCanFolderMapper trashCanFolderMapper;


    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected DndDomainService<UniverseFolder> baseDndDomainService() {
        return dndDomainService;
    }

    @Override
    protected TrashCanFolderDomainService trashCanFolderDomainService() { return this.trashCanFolderDomainService; }

    @Override
    protected ProjectAdapter projectAdapter() { return this.projectAdapter; }

    @Override
    protected TrashCanFolderMapper trashCanFolderMapper() { return this.trashCanFolderMapper; }
}
