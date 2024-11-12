package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.api.dnd.file.model.dto.response.FileInfoListResponse;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReadCastUseCase implements ReadDndUseCase<CastInfoResponse, FileInfoListResponse> {

    private final CastNodeMapper castNodeMapper;
    private final CastNodeDomainService castNodeDomainService;

    public CastInfoResponse executeRetrieve(Long castId){
        CastNode castnode = castNodeDomainService.getEntity(castId);
        return castNodeMapper.toCastInfoResponse(castnode);
    }

    @Deprecated(forRemoval = true)
    public FileInfoListResponse executeList(Long castFolderId) {
        List<CastNode> entityList = castNodeDomainService.getEntityList(castFolderId);
        return castNodeMapper.toListResponse(entityList);
    }
}
