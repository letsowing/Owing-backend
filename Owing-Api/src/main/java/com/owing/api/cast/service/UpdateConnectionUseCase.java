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
public class UpdateConnectionUseCase {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeAdapter castNodeAdapter;
    private final CastNodeMapper castNodeMapper;

    @Transactional
    public Optional<CastRelationshipInfoResponse> execute(Long relationshipId, UpdateCastRelationshipRequest updateRequest) {
        CastRelationshipProjection castRelationshipProjection = castNodeAdapter.findCastRelationshipById(relationshipId);
        CastRelationship castRelationship = castNodeMapper.toCastRelationship(castRelationshipProjection);

        // Update Handle
        if (castRelationship.getSourceId().equals(updateRequest.sourceId())
                && castRelationship.getTargetId().equals(updateRequest.targetId()))
        {
            castNodeDomainService.updateCastRelationshipHandle(castRelationship, updateRequest.sourceHandle(), updateRequest.targetHandle());
            return Optional.empty();
        }

        // Create Relationship Again
        CastRelationship newRelationship = castNodeMapper.toCastRelationship(castRelationship.getLabel(), updateRequest);
        castNodeDomainService.deleteCastRelationship(castRelationship);

        CastRelationshipProjection connection = castNodeDomainService.handleCastRelationship(updateRequest.type(), newRelationship);
        return Optional.of(castNodeMapper.toInfoResponse(connection, updateRequest.type()));
    }

    public void executeLabel(Long relationshipId, UpdateCastRelationshipLabelRequest updateTitleRequest) {
        CastRelationshipProjection castRelationshipProjection = castNodeAdapter.findCastRelationshipById(relationshipId);
        CastRelationship castRelationship = castNodeMapper.toCastRelationship(castRelationshipProjection);
        castNodeDomainService.updateCastRelationshipLabel(castRelationship, updateTitleRequest.label());
    }
}
