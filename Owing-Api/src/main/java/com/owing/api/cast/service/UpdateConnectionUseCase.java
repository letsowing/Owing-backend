package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.UpdateCastRelationshipLabelRequest;
import com.owing.api.cast.model.dto.request.UpdateCastRelationshipHandleRequest;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastRelationship;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class UpdateConnectionUseCase {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeAdapter castNodeAdapter;
    private final CastNodeMapper castNodeMapper;

    public void executeHandle(Long relationshipId, UpdateCastRelationshipHandleRequest updateRequest) {
        CastRelationshipProjection castRelationshipProjection = castNodeAdapter.findCastRelationshipById(relationshipId);
        CastRelationship castRelationship = castNodeMapper.toCastRelationship(castRelationshipProjection);
        castNodeDomainService.updateCastRelationshipHandle(castRelationship, updateRequest.sourceHandle(), updateRequest.targetHandle());
    }

    public void executeLabel(Long relationshipId, UpdateCastRelationshipLabelRequest updateTitleRequest) {
        CastRelationshipProjection castRelationshipProjection = castNodeAdapter.findCastRelationshipById(relationshipId);
        CastRelationship castRelationship = castNodeMapper.toCastRelationship(castRelationshipProjection);
        castNodeDomainService.updateCastRelationshipLabel(castRelationship, updateTitleRequest.label());
    }
}
