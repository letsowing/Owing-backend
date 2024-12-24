package com.owing.node.folder.cast.adapter;

import java.util.List;

import org.springframework.data.neo4j.core.Neo4jTemplate;

import com.owing.common.annotation.Adaptor;
import com.owing.core.dnd.base.repository.DndRepository;
import com.owing.core.dnd.folder.adapter.DndFolderAdapter;
import com.owing.node.folder.cast.error.code.CastFolderNodeErrorCode;
import com.owing.node.folder.cast.error.exception.CastFolderNodeNotFoundException;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderDeleteProjection;
import com.owing.node.folder.cast.model.projection.CastFolderPositionProjection;
import com.owing.node.folder.cast.model.projection.CastFolderTitleProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;

import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class CastFolderNodeAdapter extends DndFolderAdapter<CastFolderNode> {

    private final CastFolderNodeRepository castFolderNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

    public CastFolderNode findById(Long castFolderNodeId) {
        return castFolderNodeRepository.findById(castFolderNodeId)
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
    public CastFolderNode updatePosition(CastFolderNode castFolderNode) {
        CastFolderPositionProjection castFolderPositionProjection = CastFolderPositionProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(castFolderPositionProjection);
        return castFolderNode;
    }

    @Override
    public void delete(CastFolderNode entity) {
        // castFolderNodeRepository.deleteFolderById(entity.getId());
        entity.delete();
        CastFolderDeleteProjection deleteProjection = CastFolderDeleteProjection.from(entity);
        neo4jTemplate.save(CastFolderNode.class).one(deleteProjection);
    }

    @Override
    public CastFolderNode updateName(CastFolderNode entity) {
        CastFolderTitleProjection titleProjection = CastFolderTitleProjection.from(entity);
        neo4jTemplate.save(CastFolderNode.class).one(titleProjection);
        return entity;
    }

    // =====Bean Setting=====
    @Override
    protected DndRepository<CastFolderNode> dndRepository() {
        return castFolderNodeRepository;
    }
}
