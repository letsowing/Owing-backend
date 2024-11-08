package com.owing.node.domains.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.common.constant.CastConstant;
import com.owing.node.domains.cast.adaptor.CastNodeAdaptor;
import com.owing.node.domains.cast.model.*;
import com.owing.node.domains.cast.model.projection.CastCoordinateProjection;
import com.owing.node.domains.cast.model.projection.CastDeleteProjection;
import com.owing.node.domains.cast.model.projection.CastInfoProjection;
import com.owing.node.domains.cast.repository.CastNodeRepository;
import com.owing.node.folder.cast.model.CastFolderNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastNodeDomainService {

    private final CastNodeRepository castNodeRepository;
    private final CastNodeAdaptor castNodeAdaptor;
    private final Neo4jTemplate neo4jTemplate;

    @Transactional
    public CastNode createCastNode(CastNode castNode, CastFolderNode castFolderNode) {
        // TODO position 기본값으로 변경
        castNode.updatePosition(0L);
        castNode.updateCoordinate(
                CastConstant.DEFAULT_COORDINATE_X,
                CastConstant.DEFAULT_COORDINATE_Y
        );
        castNode.connectFolder(castFolderNode);
        return castNodeRepository.save(castNode);
    }

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
    public void deleteCastNode(CastNode castNode) {
        castNode.delete();
        CastDeleteProjection deleteProjection = CastDeleteProjection.from(castNode);
        neo4jTemplate.save(CastNode.class).one(deleteProjection);
    }
}
