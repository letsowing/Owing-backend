package com.owing.node.folder.cast.adapter;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.adapter.BaseFolderAdapter;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;
import com.owing.node.folder.cast.error.exception.CastFolderNodeNotFoundException;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderPositionProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class CastFolderNodeAdapter extends BaseFolderAdapter<CastFolderNode> {

    private final CastFolderNodeRepository castFolderNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

    public CastFolderNode findOneById(Long castFolderNodeId) {
        return castFolderNodeRepository.findOneById(castFolderNodeId)
                .orElseThrow(() -> CastFolderNodeNotFoundException.of(
                        CastFolderNodeErrorCode.NODE_NOT_FOUND,
                        "cast folder id: %d".formatted(castFolderNodeId)
                ));
    }

    public CastFolderNode findOneWithRelationshipById(Long castFolderNodeId) {
        return castFolderNodeRepository.findOneWithRelationshipById(castFolderNodeId)
                .orElseThrow(() -> CastFolderNodeNotFoundException.of(
                        CastFolderNodeErrorCode.NODE_NOT_FOUND,
                        "cast folder id: %d".formatted(castFolderNodeId)
                ));
    }

    public List<CastFolderNode> findAllWithRelationshipByProjectId(Long projectId) {
        return castFolderNodeRepository.findAllWithRelationshipByProjectId(projectId);
    }

    /* TODO DomainService로 위치이동 고려 */
    private CastFolderNode savePosition(CastFolderNode castFolderNode) {
        CastFolderPositionProjection castFolderPositionProjection = CastFolderPositionProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(castFolderPositionProjection);
        return castFolderNode;
    }

    @Transactional("neo4jTransactionManager")
    @Override
    public CastFolderNode save(CastFolderNode entity) {
        return this.savePosition(entity);
    }

    // =====Bean Setting=====
    @Override
    protected BaseDndRepository<CastFolderNode> dndRepository() {
        return castFolderNodeRepository;
    }
}
