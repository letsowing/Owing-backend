package com.owing.node.domains.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.service.BaseFileDomainService;
import com.owing.node.common.constant.CastConstant;
import com.owing.node.domains.cast.adapter.CastNodeAdapter;
import com.owing.node.domains.cast.model.*;
import com.owing.node.domains.cast.model.projection.*;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastNodeDomainService extends BaseFileDomainService<CastNode, CastFolderNode> {

    private final CastNodeAdapter castNodeAdapter;
    private final CastShiftOrderingStrategy castShiftOrderingStrategy;
    private final CastNodeRepository castNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

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
            neo4jTemplate.save(CastNode.class).one(castNode);
        }
    }

    @Transactional
    public void restoreById(Long castId) {
        castNodeRepository.restoreById(castId);
    }

    // =====Cast Relationship=====
    @Transactional
    public CastNode createConnection(CastNode sourceCast, CastRelationship relationship) {
        sourceCast.connectCast(relationship);
        return castNodeRepository.save(sourceCast);
    }

    @Transactional
    public CastNode createBiconnection(CastNode sourceCast, CastRelationship relationship) {
        sourceCast.biconnectCast(relationship);
        return castNodeRepository.save(sourceCast);
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
