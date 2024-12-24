package com.owing.node.domains.cast.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.DndAdapter;
import com.owing.core.dnd.file.service.DndFileDomainService;
import com.owing.core.dnd.orderStrategy.OrderingStrategy;
import com.owing.node.common.constant.CastConstant;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastNodeRelationshipException;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.CastNodeInfo;
import com.owing.node.domains.cast.model.CastRelationship;
import com.owing.node.domains.cast.model.ConnectionHandle;
import com.owing.node.domains.cast.model.ConnectionType;
import com.owing.node.domains.cast.model.Coordinate;
import com.owing.node.domains.cast.model.projection.CastCoordinateProjection;
import com.owing.node.domains.cast.model.projection.CastGraphNodeProjection;
import com.owing.node.domains.cast.model.projection.CastGraphRelationshipProjection;
import com.owing.node.domains.cast.model.projection.CastInfoProjection;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "neo4jTransactionManager")
public class CastNodeDomainService extends DndFileDomainService<CastNode> {

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

    public CastNode getLastPositionCastNodeInFolder(Long castFolderId) {
        List<CastNode> castNodeList = castNodeAdapter.findByFolderIdOrderByPositionDescLimit(castFolderId, 1L);
        return castNodeList.isEmpty() ? null : castNodeList.getFirst();
    }

    @Transactional("neo4jTransactionManager")
    public void updateCastNodeInfo(CastNode castNode, CastNodeInfo castNodeInfo) {
        castNode.updateInfo(castNodeInfo);
        neo4jTemplate.save(CastNode.class).one(CastInfoProjection.from(castNode));
    }

    @Transactional("neo4jTransactionManager")
    public void updateCastNodeCoordinate(CastNode castNode, Coordinate coordinate) {
        boolean isUpdated = castNode.updateCoordinate(coordinate);
        if (isUpdated) {
            neo4jTemplate.save(CastNode.class).one(CastCoordinateProjection.from(castNode));
        }
    }

    @Transactional("neo4jTransactionManager")
    public void restoreById(Long castId) {
        castNodeRepository.restoreById(castId);
    }

    // =====Cast Relationship=====
    @Transactional("neo4jTransactionManager")
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

    @Transactional("neo4jTransactionManager")
    protected CastRelationshipProjection createConnection(CastRelationship relationship) {
        Boolean exists = castNodeRepository.existsCastRelationshipForConnection(relationship.getSourceId(), relationship.getTargetId());
        if (exists) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.CONNECTION_ALREADY_EXISTS);
        }
        createCastRelationship(relationship, ConnectionType.DIRECTIONAL);
        return castNodeAdapter.findConnection(relationship.getSourceId(), relationship.getTargetId());
    }

	@Transactional("neo4jTransactionManager")
    protected CastRelationshipProjection createBiconnection(CastRelationship relationship) {
        Boolean exists = castNodeRepository.existsCastRelationshipForBiconnection(relationship.getSourceId(), relationship.getTargetId());
        if (exists) {
            throw CastNodeRelationshipException.of(CastNodeErrorCode.CONNECTION_ALREADY_EXISTS);
        }
        createCastRelationship(relationship, ConnectionType.BIDIRECTIONAL);
        return castNodeAdapter.findBiconnection(relationship.getSourceId(), relationship.getTargetId());
    }

    @Transactional("neo4jTransactionManager")
    protected void createCastRelationship(CastRelationship relationship, ConnectionType connectionType) {
        castNodeRepository.createCastRelationship(
                relationship.getSourceId(), relationship.getTargetId(),
                connectionType.getValue(), relationship.getLabel(),
                relationship.getSourceHandle().name(), relationship.getTargetHandle().name()
        );
    }

    @Transactional("neo4jTransactionManager")
    public void updateCastRelationshipLabel(CastRelationship castRelationship, String label) {
        castNodeRepository.updateCastRelationshipLabel(castRelationship.getId(), label);
    }

    @Transactional("neo4jTransactionManager")
    public void updateCastRelationshipHandle(CastRelationship castRelationship, ConnectionHandle sourceHandle, ConnectionHandle targetHandle) {
        castNodeRepository.updateCastRelationshipHandle(castRelationship.getId(), sourceHandle.name(), targetHandle.name());
    }

    @Transactional("neo4jTransactionManager")
    public void deleteCastRelationship(CastRelationship castRelationship) {
        castNodeRepository.deleteCastRelationshipById(castRelationship.getId());
    }

    // =====CastFolder Relationship=====
    @Transactional("neo4jTransactionManager")
    public void attachFolder(CastNode castNode, Long castFolderId) {
        castNodeRepository.mergeIncludeRelationship(castNode.getId(), castFolderId);
    }

    @Transactional("neo4jTransactionManager")
    public void detachFolder(CastNode castNode, Long castFolderId) {
        castNodeRepository.deleteIncludeRelationship(castNode.getId(), castFolderId);
    }

    // =====super() Cast CRUD=====
    @Override
    @Transactional("neo4jTransactionManager")
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
	@Transactional("neo4jTransactionManager")
    public CastNode updateName(CastNode entity, CastNode newEntity) {
        entity.updateName(newEntity.getName());
        CastTitleProjection titleProjection = CastTitleProjection.from(entity);
        neo4jTemplate.save(CastNode.class).one(titleProjection);
        return entity;
    }

    @Override
	@Transactional("neo4jTransactionManager")
    public void deleteEntity(CastNode castNode) {
        castShiftOrderingStrategy.reorderEntity(castNode);
        castNode.delete();
        CastDeleteProjection deleteProjection = CastDeleteProjection.from(castNode);
        neo4jTemplate.save(CastNode.class).one(deleteProjection);
    }

    // Bean Setting
    @Override
    protected DndAdapter<CastNode> dndAdapter() {
        return this.castNodeAdapter;
    }

    @Override
    protected OrderingStrategy<CastNode> orderingStrategy() {
        return this.castShiftOrderingStrategy;
    }

}
