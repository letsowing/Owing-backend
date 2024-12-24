package com.owing.api.cast.service;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.DndDomainService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastUseCase extends DeleteFileUseCase<CastNode> {

    private final CastNodeDomainService castNodeDomainService;
    private final MemberUtils memberUtils;
    private final TrashCanDomainService trashCanDomainService;
    private final TrashCanMapper trashCanMapper;

    // Bean Setting
    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected DndDomainService<CastNode> baseDndDomainService() {
        return this.castNodeDomainService;
    }

    @Override
    protected TrashCanDomainService trashCanDomainService() {
        return trashCanDomainService;
    }

    @Override
    protected TrashCanMapper trashCanMapper() {
        return trashCanMapper;
    }
}
