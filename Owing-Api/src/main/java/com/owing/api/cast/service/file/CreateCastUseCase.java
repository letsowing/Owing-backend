package com.owing.api.cast.service.file;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.common.util.MemberUtils;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.base.service.DndService;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCastUseCase extends CreateFileUseCase<CastNode, CastFolderNode> {

    private final CastNodeService castNodeDomainService;
    private final CastNodeMapper castNodeMapper;
    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final MemberUtils memberUtils;

    public CastInfoResponse executeFull(CreateCastRequest createCastRequest) {
        CastNode castNode = castNodeMapper.toEntity(createCastRequest);
        CastFolderNode castFolderNode = castFolderNodeAdapter.findById(createCastRequest.folderId());

        castNode.connectFolder(castFolderNode);
        CastNode savedCastNode = castNodeDomainService.create(castNode);
        return castNodeMapper.toInfoResponse( savedCastNode);
    }

    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected DndService<CastNode> fileService() {
        return this.castNodeDomainService;
    }

    @Override
    protected BaseFileMapper<CastNode, CastFolderNode> fileMapper() {
        return this.castNodeMapper;
    }

    @Override
    protected DndAdapter<CastFolderNode> folderAdapter() {
        return castFolderNodeAdapter;
    }
}
