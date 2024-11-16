package com.owing.node.domains.cast.adapter;

import java.util.List;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.file.adapter.BaseFileAdapter;
import com.owing.node.common.model.projection.CastRelationshipProjection;
import com.owing.node.domains.cast.error.code.CastNodeErrorCode;
import com.owing.node.domains.cast.error.exception.CastNodeNotFoundException;
import com.owing.node.domains.cast.error.exception.CastRelationshipNotFoundException;
import com.owing.node.domains.cast.model.CastNode;
import com.owing.node.domains.cast.model.projection.*;
import com.owing.node.domains.cast.repository.CastNodeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

@Adaptor
@RequiredArgsConstructor
public class CastNodeAdapter extends BaseFileAdapter<CastNode> {

    private final CastNodeRepository castNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

    public CastNode findOneById(Long castId) {
        return castNodeRepository.findOneById(castId)
                .orElseThrow(() -> CastNodeNotFoundException.of(
                        CastNodeErrorCode.CAST_NODE_NOT_FOUND,
                        "Requested Cast Node Id: %d".formatted(castId)
                ));
    }

    public List<CastNode> findByFolderIdOrderByPositionDescLimit(Long castFolderId, Long limit) {
        return castNodeRepository.findByFolderIdOrderByPositionDescLimit(castFolderId, limit);
    }

    public CastRelationshipProjection findConnection(Long sourceId, Long targetId) {
        return castNodeRepository.findConnection(sourceId, targetId)
                .orElseThrow(() -> CastRelationshipNotFoundException.of(
                        CastNodeErrorCode.RELATIONSHIP_NOT_FOUND,
                        "Source Id: %d, Target Id: %d".formatted(sourceId, targetId)
                ));
    }

    public CastRelationshipProjection findBiconnection(Long sourceId, Long targetId) {
        return castNodeRepository.findBiconnection(sourceId, targetId)
                .orElseThrow(() -> CastRelationshipNotFoundException.of(
                        CastNodeErrorCode.RELATIONSHIP_NOT_FOUND,
                        "Source Id: %d, Target Id: %d".formatted(sourceId, targetId)
                ));
    }

    public CastRelationshipProjection findCastRelationshipById(Long relationshipId) {
        return castNodeRepository.findCastRelationshipById(relationshipId)
                .orElseThrow(() -> CastRelationshipNotFoundException.of(
                        CastNodeErrorCode.RELATIONSHIP_NOT_FOUND,
                        "relationship id: %d".formatted(relationshipId)
                ));
    }

    public List<CastGraphNodeProjection> findGraphCastByProjectId(Long projectId) {
        return castNodeRepository.findGraphCastByProjectId(projectId);
    }

    public List<CastGraphRelationshipProjection> findGraphCastRelationshipByProjectId(Long projectId) {
        return castNodeRepository.findGraphCastRelationshipByProjectId(projectId);
    }

    public List<CastAiProjection> findAllCastForAiPrompt(Long projectId) {
        List<CastAiProjection> allCastForAiPrompt = castNodeRepository.findAllCastForAiPrompt(projectId);
        return allCastForAiPrompt;
    }

    public List<CastRelationshipAiProjection> findAllCastRelationshipForAiPrompt(Long projectId) {
		List<CastRelationshipAiProjection> allCastRelationshipForAiPrompt = castNodeRepository.findAllCastRelationshipForAiPrompt(
			projectId);
		return allCastRelationshipForAiPrompt;
	}

    /* TODO DomainService로 위치이동 고려 */
    private CastNode savePosition(CastNode castNode) {
        CastPositionProjection castPositionProjection = CastPositionProjection.from(castNode);
        neo4jTemplate.save(CastNode.class).one(castPositionProjection);
        return castNode;
    }

    @Transactional
    @Override
    public CastNode save(CastNode entity) {
        return this.savePosition(entity);
    }

    @Transactional
    public void incrementPositionAfter(long targetPosition, Long projectId) {
        castNodeRepository.incrementPositionAfter(targetPosition, projectId);
    }

    // Bean Setting
    @Override
    protected BaseDndRepository<CastNode> dndRepository() {
        return this.castNodeRepository;
    }
}
