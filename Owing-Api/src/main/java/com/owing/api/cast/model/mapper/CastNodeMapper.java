package com.owing.api.cast.model.mapper;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.response.CastFileResponse;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.common.annotation.Mapper;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastRelationship;
import com.owing.node.domains.cast.model.ConnectionType;

@Mapper
public class CastNodeMapper {

    public CastNode toEntity(CreateCastRequest createCastRequest) {
        return CastNode.builder()
                .name(createCastRequest.name())
                .age(createCastRequest.age())
                .gender(createCastRequest.gender())
                .role(createCastRequest.role())
                .description(createCastRequest.description())
                .imageUrl(createCastRequest.imageUrl())
                .coordinate(createCastRequest.coordinate())
                .build();
    }

    public CastRelationship toRelationship(CreateConnectionRequest createConnectionRequest, CastNode target) {
        return CastRelationship.builder()
                .label(createConnectionRequest.label())
                .sourceId(createConnectionRequest.sourceId())
                .sourceHandle(createConnectionRequest.sourceHandle())
                .targetId(createConnectionRequest.targetId())
                .targetHandle(createConnectionRequest.targetHandle())
                .targetNode(target)
                .build();
    }

    public CastInfoResponse toInfoResponse(CastNode castNode) {
        return new CastInfoResponse(
                castNode.getId(),
                castNode.getName(),
                castNode.getAge(),
                castNode.getGender(),
                castNode.getRole(),
                castNode.getDescription(),
                castNode.getImageUrl(),
                castNode.getCoordinate(),
                castNode.getPosition()
        );
    }

    public CastRelationshipInfoResponse toInfoResponse(CastRelationshipProjection relationship, ConnectionType type) {
        return new CastRelationshipInfoResponse(
                relationship.relationshipId(),
                relationship.label(),
                type,
                relationship.sourceId(),
                relationship.sourceHandle(),
                relationship.targetId(),
                relationship.targetHandle()
        );
    }

    public CastFileResponse toFileResponse(CastNode castNode) {
        return new CastFileResponse(
                castNode.getId(),
                castNode.getName(),
                castNode.getDescription(),
                castNode.getImageUrl(),
                castNode.getPosition()
        );
    }
}
