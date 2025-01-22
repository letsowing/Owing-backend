package com.owing.api.cast.service;

import java.util.List;

import com.owing.api.cast.model.dto.response.CastGraphResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.model.projection.CastGraphNodeProjection;
import com.owing.node.domains.cast.model.projection.CastGraphRelationshipProjection;
import com.owing.node.domains.cast.service.CastNodeService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadCastUseCase{
    private final CastNodeMapper castNodeMapper;
    private final CastNodeService castNodeDomainService;

    public CastGraphResponse executeGraph(Long projectId) {
        List<CastGraphNodeProjection> graphNode = castNodeDomainService.getGraphNode(projectId);
        List<CastGraphRelationshipProjection> graphRelationship = castNodeDomainService.getGraphRelationship(projectId);
        return castNodeMapper.toGraphResponse(graphNode, graphRelationship);
    }
}
