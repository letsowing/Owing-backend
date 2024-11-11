package com.owing.api.universe.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteUniverseFolderUseCase extends DeleteFolderUseCase<UniverseFolder> {

    private final MemberUtils memberUtils;
    private final BaseDndDomainService<UniverseFolder> baseDndDomainService;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected BaseDndDomainService<UniverseFolder> baseDndDomainService() {
        return baseDndDomainService;
    }
}
