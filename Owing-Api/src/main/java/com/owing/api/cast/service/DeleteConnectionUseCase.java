package com.owing.api.cast.service;

import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.CastRelationship;
import com.owing.node.domains.cast.service.CastNodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteConnectionUseCase {

    private final CastNodeService castNodeDomainService;
    private final CastNodeAdapter castNodeAdapter;
    private final CastNodeMapper castNodeMapper;

    @Transactional("neo4jTransactionManager")
    public void execute(Long relationshipId) {
        CastRelationshipProjection castRelationshipProjection = castNodeAdapter.findCastRelationshipById(relationshipId);
        CastRelationship castRelationship = castNodeMapper.toCastRelationship(castRelationshipProjection);
        castNodeDomainService.deleteCastRelationship(castRelationship);
    }
}
