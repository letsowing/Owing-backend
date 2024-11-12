package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.mapper.BaseFileMapper;
import com.owing.api.dnd.file.service.CreateFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCastUseCase extends CreateFileUseCase<CastNode, CastFolderNode> {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeMapper castNodeMapper;
    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final MemberUtils memberUtils;

    @Override
    public CastInfoResponse execute(AddFileRequest addFileRequest) {
        CastFolderNode castFolderNode = castFolderNodeAdapter.findOneById(addFileRequest.folderId());
        CastNode castNode = castNodeMapper.toEntity(addFileRequest, castFolderNode);

        CastNode savedCastNode = castNodeDomainService.createEntity(castNode);
        return castNodeMapper.toInfoResponse(savedCastNode);
    }

    public CastInfoResponse executeFull(CreateCastRequest createCastRequest) {
        CastNode castNode = castNodeMapper.toEntity(createCastRequest);
        CastFolderNode castFolderNode = castFolderNodeAdapter.findOneById(createCastRequest.folderId());

        castNode.connectFolder(castFolderNode);
        CastNode savedCastNode = castNodeDomainService.createEntity(castNode);
        return castNodeMapper.toInfoResponse( savedCastNode);
    }

    @Override
    protected MemberUtils memberUtils() {
        return this.memberUtils;
    }

    @Override
    protected BaseDndDomainService<CastNode> baseDndDomainService() {
        return this.castNodeDomainService;
    }

    @Override
    protected BaseFileMapper<CastNode, CastFolderNode> dndMapper() {
        return this.castNodeMapper;
    }

    @Override
    protected BaseDndAdapter<CastFolderNode> folderAdapter() {
        return null;
    }
}
