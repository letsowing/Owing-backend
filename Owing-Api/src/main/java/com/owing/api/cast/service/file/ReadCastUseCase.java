package com.owing.api.cast.service.file;

import java.util.List;

import com.owing.api.cast.model.dto.response.CastGraphResponse;
import com.owing.api.cast.model.dto.response.CastInfoWithFolderResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.api.dnd.base.service.ReadDndUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.projection.CastGraphNodeProjection;
import com.owing.node.domains.cast.model.projection.CastGraphRelationshipProjection;
import com.owing.node.domains.cast.service.CastNodeService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class ReadCastUseCase implements ReadDndUseCase {

    private final CastNodeMapper castNodeMapper;
    private final CastNodeAdapter castNodeAdapter;
    private final CastNodeService castNodeDomainService;

    public CastInfoWithFolderResponse executeRetrieve(Long castId){
        CastNode castnode = castNodeAdapter.findById(castId);
        return castNodeMapper.toInfoWithFolderResponse(castnode);
    }

    public CastGraphResponse executeGraph(Long projectId) {
        List<CastGraphNodeProjection> graphNode = castNodeDomainService.getGraphNode(projectId);
        List<CastGraphRelationshipProjection> graphRelationship = castNodeDomainService.getGraphRelationship(projectId);
        return castNodeMapper.toGraphResponse(graphNode, graphRelationship);
    }
}
