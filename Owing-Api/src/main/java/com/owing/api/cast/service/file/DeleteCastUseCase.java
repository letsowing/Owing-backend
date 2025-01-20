package com.owing.api.cast.service.file;

import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteCastUseCase extends DeleteFileUseCase<CastNode> {

    private final CastNodeService castNodeService;
    private final MemberUtils memberUtils;
    private final TrashCanDomainService trashCanDomainService;
    private final TrashCanMapper trashCanMapper;
    private final CastNodeAdapter castNodeAdapter;

    // Bean Setting
    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected DndService<CastNode> fileService() {
        return this.castNodeService;
    }

    @Override
    protected DndAdapter<CastNode> fileAdapter() {
        return castNodeAdapter;
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
