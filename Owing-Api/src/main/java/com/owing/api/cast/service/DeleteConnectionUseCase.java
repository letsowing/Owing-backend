package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.UpdateCastRelationshipLabelRequest;
import com.owing.api.cast.model.dto.request.UpdateCastRelationshipRequest;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastRelationship;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@UseCase
@RequiredArgsConstructor
public class DeleteConnectionUseCase {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeAdapter castNodeAdapter;
    private final CastNodeMapper castNodeMapper;

    @Transactional
    public void execute(Long relationshipId) {
        CastRelationshipProjection castRelationshipProjection = castNodeAdapter.findCastRelationshipById(relationshipId);
        CastRelationship castRelationship = castNodeMapper.toCastRelationship(castRelationshipProjection);
        castNodeDomainService.deleteCastRelationship(castRelationship);
    }
}
