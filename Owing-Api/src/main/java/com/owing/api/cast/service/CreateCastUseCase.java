package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateCastUseCase {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeMapper castNodeMapper;
    private final CastFolderNodeAdapter castFolderNodeAdapter;

    public CastInfoResponse execute(CreateCastRequest createCastRequest) {
        CastNode castNode = castNodeMapper.toEntity(createCastRequest);
        CastFolderNode castFolderNode = castFolderNodeAdapter.findOneById(createCastRequest.folderId());

        CastNode savedCastNode = castNodeDomainService.createCastNode(castNode, castFolderNode);
        return castNodeMapper.toInfoResponse(savedCastNode);
    }
}
