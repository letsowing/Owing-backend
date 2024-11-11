package com.owing.api.cast.service;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.service.CastFolderNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastFolderUseCase extends DeleteFolderUseCase<CastFolderNode> {

    private final CastFolderNodeDomainService castFolderNodeDomainService;
    private final MemberUtils memberUtils;

    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected BaseDndDomainService<CastFolderNode> baseDndDomainService() {
        return this.castFolderNodeDomainService;
    }

}
