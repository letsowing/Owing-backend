package com.owing.api.cast.model.mapper;

import java.util.List;

import com.owing.api.cast.model.dto.request.CreateCastRequest;
import com.owing.api.cast.model.dto.request.CreateConnectionRequest;
import com.owing.api.cast.model.dto.request.UpdateCastCoordinateRequest;
import com.owing.api.cast.model.dto.request.UpdateCastInfoRequest;
import com.owing.api.cast.model.dto.request.UpdateCastRelationshipRequest;
import com.owing.api.cast.model.dto.response.CastFileResponse;
import com.owing.api.cast.model.dto.response.CastGraphResponse;
import com.owing.api.cast.model.dto.response.CastInfoResponse;
import com.owing.api.cast.model.dto.response.CastInfoWithFolderResponse;
import com.owing.api.cast.model.dto.response.CastRelationshipInfoResponse;
import com.owing.api.dnd.dto.request.AddFileRequest;
import com.owing.api.dnd.dto.request.UpdateFileNameRequest;
import com.owing.api.dnd.mapper.DndFileMapper;
import com.owing.common.annotation.Mapper;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastNodeInfo;
import com.owing.node.domains.cast.model.CastRelationship;
import com.owing.node.domains.cast.model.ConnectionType;
import com.owing.node.domains.cast.model.Coordinate;
import com.owing.node.domains.cast.model.projection.CastGraphNodeProjection;
import com.owing.node.domains.cast.model.projection.CastGraphRelationshipProjection;
import com.owing.node.folder.cast.model.CastFolderNode;

@Mapper
public class CastNodeMapper implements DndFileMapper<CastNode, CastFolderNode> {

    @Override
    public CastNode toEntity(AddFileRequest addDndRequest, CastFolderNode folder) {
        return CastNode.builder()
                .name(addDndRequest.name())
                .folder(folder)
                .build();
    }

    public CastNode toEntity(UpdateFileNameRequest updateDndRequest) {
        return CastNode.builder()
                .name(updateDndRequest.name())
                .build();
    }

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

    public CastNodeInfo toCastNodeInfo(UpdateCastInfoRequest updateCastInfoRequest) {
        return new CastNodeInfo(
                updateCastInfoRequest.name(),
                updateCastInfoRequest.age(),
                updateCastInfoRequest.gender(),
                updateCastInfoRequest.role(),
                updateCastInfoRequest.description(),
                updateCastInfoRequest.imageUrl()
        );
    }

    public Coordinate toCoordinate(UpdateCastCoordinateRequest updateCastCoordinateRequest) {
        return new Coordinate(
                updateCastCoordinateRequest.x(),
                updateCastCoordinateRequest.y()
        );
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

    @Override
    public CastInfoResponse toInfoResponse(CastNode castNode) {
        return new CastInfoResponse(
                castNode.getId(),
                castNode.getName(),
                castNode.getAge(),
                castNode.getGender(),
                castNode.getRole(),
                castNode.getDescription(),
                castNode.getImageUrl(),
                castNode.getCoordinate()
        );
    }

    public CastInfoWithFolderResponse toInfoWithFolderResponse(CastNode castNode) {
        return new CastInfoWithFolderResponse(castNode.getParentId(), this.toInfoResponse(castNode));
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
                castNode.getImageUrl()
        );
    }

    public CastGraphResponse toGraphResponse(List<CastGraphNodeProjection> graphCast, List<CastGraphRelationshipProjection> graphCastRelationship) {
        return new CastGraphResponse(graphCast, graphCastRelationship);
    }

    public CastRelationship toCastRelationship(CastRelationshipProjection castRelationshipProjection) {
        return CastRelationship.builder()
                .id(castRelationshipProjection.relationshipId())
                .label(castRelationshipProjection.label())
                .sourceId(castRelationshipProjection.sourceId())
                .targetId(castRelationshipProjection.targetId())
                .sourceHandle(castRelationshipProjection.sourceHandle())
                .targetHandle(castRelationshipProjection.targetHandle())
                .build();
    }

    public CastRelationship toCastRelationship(String label, UpdateCastRelationshipRequest updateRequest) {
        return CastRelationship.builder()
                .label(label)
                .sourceId(updateRequest.sourceId())
                .targetId(updateRequest.targetId())
                .sourceHandle(updateRequest.sourceHandle())
                .targetHandle(updateRequest.targetHandle())
                .build();
    }
}
