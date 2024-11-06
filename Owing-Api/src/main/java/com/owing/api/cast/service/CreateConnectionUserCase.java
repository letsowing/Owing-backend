package com.owing.api.cast.service;

import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.cast.model.mapper.CastNodeMapper;
import com.owing.common.annotation.UseCase;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.adaptor.CastNodeAdaptor;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastRelationship;
import com.owing.node.domains.cast.model.ConnectionType;
import com.owing.node.domains.cast.service.CastNodeDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class CreateConnectionUserCase {

    private final CastNodeDomainService castNodeDomainService;
    private final CastNodeAdaptor castNodeAdaptor;
    private final CastNodeMapper castNodeMapper;

    public CastRelationshipInfoResponse execute(CreateConnectionRequest createConnectionRequest) {
        CastNode sourceCast = castNodeAdaptor.findById(createConnectionRequest.sourceId());
        CastNode targetCast = castNodeAdaptor.findById(createConnectionRequest.targetId());

        CastRelationship connection = castNodeMapper.toRelationship(createConnectionRequest, targetCast);

        CastRelationshipProjection savedConnection;
        if (createConnectionRequest.type().equals(ConnectionType.DIRECTIONAL)) {
            castNodeDomainService.createConnection(sourceCast, connection);
            savedConnection = castNodeAdaptor.findConnection(sourceCast.getId(), targetCast.getId());
        } else {
            castNodeDomainService.createBiconnection(sourceCast, connection);
            savedConnection = castNodeAdaptor.findBiconnection(sourceCast.getId(), targetCast.getId());
        }

        return castNodeMapper.toInfoResponse(savedConnection, createConnectionRequest.type());
    }
}
