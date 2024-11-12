package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.response.CastGraphResponse;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.projection.CastGraphNodeProjection;
import com.owing.node.domains.cast.model.projection.CastGraphRelationshipProjection;
import com.owing.node.domains.cast.service.CastNodeDomainService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ReadCastUseCase implements ReadDndUseCase {

    private final CastNodeMapper castNodeMapper;
    private final CastNodeDomainService castNodeDomainService;

    public CastInfoResponse executeRetrieve(Long castId){
        CastNode castnode = castNodeDomainService.getEntity(castId);
        return castNodeMapper.toInfoResponse(castnode);
    }

    public CastGraphResponse executeGraph(Long projectId) {
        List<CastGraphNodeProjection> graphNode = castNodeDomainService.getGraphNode(projectId);
        List<CastGraphRelationshipProjection> graphRelationship = castNodeDomainService.getGraphRelationship(projectId);
        return castNodeMapper.toGraphResponse(graphNode, graphRelationship);
    }
}
