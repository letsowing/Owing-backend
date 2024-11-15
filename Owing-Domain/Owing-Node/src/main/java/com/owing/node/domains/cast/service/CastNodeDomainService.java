package com.owing.node.domains.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.service.BaseFileDomainService;
import com.owing.node.common.constant.CastConstant;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastNodeRelationshipException;
import com.owing.node.domains.cast.model.*;
import com.owing.node.domains.cast.model.projection.*;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastNodeDomainService extends BaseFileDomainService<CastNode, CastFolderNode> {

    private final CastNodeAdapter castNodeAdapter;
    private final CastShiftOrderingStrategy castShiftOrderingStrategy;
    private final CastNodeRepository castNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

    private final Map<ConnectionType, Function<CastRelationship, CastRelationshipProjection>> castRelationshipHandler = Map.of(
            ConnectionType.DIRECTIONAL, this::createConnection,
            ConnectionType.BIDIRECTIONAL, this::createBiconnection
    );

    public List<CastGraphNodeProjection> getGraphNode(Long projectId) {
        return castNodeAdapter.findGraphCastByProjectId(projectId);
    }

    public List<CastGraphRelationshipProjection> getGraphRelationship(Long projectId) {
        return castNodeAdapter.findGraphCastRelationshipByProjectId(projectId);
    }

    @Transactional
    public void updateCastNodeInfo(CastNode castNode, CastNodeInfo castNodeInfo) {
        castNode.updateInfo(castNodeInfo);
        neo4jTemplate.save(CastNode.class).one(CastInfoProjection.from(castNode));
    }

    @Transactional
    public void updateCastNodeCoordinate(CastNode castNode, Coordinate coordinate) {
        boolean isUpdated = castNode.updateCoordinate(coordinate);
        if (isUpdated) {
            neo4jTemplate.save(CastNode.class).one(CastCoordinateProjection.from(castNode));
        }
    }

    @Transactional
    public void restoreById(Long castId) {
        castNodeRepository.restoreById(castId);
    }

    // =====Cast Relationship=====
    @Transactional
    public CastRelationshipProjection handleCastRelationship(ConnectionType type, CastRelationship relationship) {
        if (relationship.getSourceId().equals(relationship.getTargetId())) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.ILLEGAL_ARGS, "Source, Target Cast가 동일합니다.");
        }

        Function<CastRelationship, CastRelationshipProjection> handler = castRelationshipHandler.get(type);
        if (ObjectUtils.isEmpty(handler)) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.ILLEGAL_TYPE_ARGS);
        }
        return handler.apply(relationship);
    }
    @Transactional
    protected CastRelationshipProjection createConnection(CastRelationship relationship) {
        Boolean exists = castNodeRepository.existsCastRelationshipForConnection(relationship.getSourceId(), relationship.getTargetId());
        if (exists) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.CONNECTION_ALREADY_EXISTS);
        }
        createCastRelationship(relationship, ConnectionType.DIRECTIONAL);
        return castNodeAdapter.findConnection(relationship.getSourceId(), relationship.getTargetId());
    }

    @Transactional
    protected CastRelationshipProjection createBiconnection(CastRelationship relationship) {
        Boolean exists = castNodeRepository.existsCastRelationshipForBiconnection(relationship.getSourceId(), relationship.getTargetId());
        if (exists) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.CONNECTION_ALREADY_EXISTS);
        }
        createCastRelationship(relationship, ConnectionType.BIDIRECTIONAL);
        return castNodeAdapter.findBiconnection(relationship.getSourceId(), relationship.getTargetId());
    }

    @Transactional
    protected void createCastRelationship(CastRelationship relationship, ConnectionType connectionType) {
        castNodeRepository.createCastRelationship(
                relationship.getSourceId(), relationship.getTargetId(),
                connectionType.getValue(), relationship.getLabel(),
                relationship.getSourceHandle().name(), relationship.getTargetHandle().name()
        );
    }

    @Transactional
    public void updateCastRelationshipLabel(CastRelationship castRelationship, String label) {
        castNodeRepository.updateCastRelationshipLabel(castRelationship.getId(), label);
    }

    @Transactional
    public void updateCastRelationshipHandle(CastRelationship castRelationship, ConnectionHandle sourceHandle, ConnectionHandle targetHandle) {
        castNodeRepository.updateCastRelationshipHandle(castRelationship.getId(), sourceHandle.name(), targetHandle.name());
    }

    @Transactional
    public void deleteCastRelationship(CastRelationship castRelationship) {
        castNodeRepository.deleteCastRelationshipById(castRelationship.getId());
    }

    // =====CastFolder Relationship=====
    @Transactional
    public void attachFolder(CastNode castNode, Long castFolderId) {
        castNodeRepository.mergeIncludeRelationship(castNode.getId(), castFolderId);
    }

    @Transactional
    public void detachFolder(CastNode castNode, Long castFolderId) {
        castNodeRepository.deleteIncludeRelationship(castNode.getId(), castFolderId);
    }

    // =====super() Cast CRUD=====
    @Override
    public CastNode getEntity(Long castId) {
        return castNodeAdapter.findOneById(castId);
    }

    @Override
    @Transactional
    public CastNode createEntity(CastNode entity) {
        long position = orderingStrategy().getNewPosition(entity.getParentId());
        entity.updatePosition(position);
        entity.updateCoordinate(
                CastConstant.DEFAULT_COORDINATE_X,
                CastConstant.DEFAULT_COORDINATE_Y
        );

        CastFolderNode castFolderNode = entity.getFolder();

        entity.updateFolder(null);
        CastNode savedCastNode = castNodeRepository.save(entity);
        return castNodeRepository.connectFolder(savedCastNode.getId(), castFolderNode.getId());
    }

    @Override
    @Transactional
    public CastNode updateName(CastNode entity, CastNode newEntity) {
        entity.updateName(newEntity.getName());
        CastTitleProjection titleProjection = CastTitleProjection.from(entity);
        neo4jTemplate.save(CastNode.class).one(titleProjection);
        return entity;
    }

    @Override
    @Transactional
    public void deleteEntity(CastNode castNode) {
        castShiftOrderingStrategy.reorderEntity(castNode);
        castNode.delete();
        CastDeleteProjection deleteProjection = CastDeleteProjection.from(castNode);
        neo4jTemplate.save(CastNode.class).one(deleteProjection);
    }

    // Bean Setting
    @Override
    protected BaseDndRepository<CastNode> dndRepository() {
        return this.castNodeRepository;
    }

    @Override
    protected BaseDndAdapter<CastNode> dndEntityAdapter() {
        return this.castNodeAdapter;
    }

    @Override
    protected OrderingStrategy<CastNode> orderingStrategy() {
        return this.castShiftOrderingStrategy;
    }

}
