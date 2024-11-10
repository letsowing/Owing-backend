package com.owing.node.folder.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderDeleteProjection;
import com.owing.node.folder.cast.model.projection.CastFolderInfoProjection;
import com.owing.node.folder.cast.model.projection.CastFolderPositionProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastFolderNodeDomainService {

    private final CastFolderNodeRepository castFolderNodeRepository;
    private final Neo4jTemplate neo4jTemplate;

    @Transactional
    public CastFolderNode createCastFolderNode(CastFolderNode castFolderNode, ProjectNode projectNode) {
        castFolderNode.connectProject(projectNode);
        // TODO: folder 로직 추가
        castFolderNode.updatePosition(0L);
        return castFolderNodeRepository.save(castFolderNode);
    }

    @Transactional
    public void deleteCastFolderNode(CastFolderNode castFolderNode) {
        castFolderNode.delete();
        CastFolderDeleteProjection deleteProjection = CastFolderDeleteProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(deleteProjection);
    }

    @Transactional
    public void updatePosition(CastFolderNode castFolderNode, Long position) {
        castFolderNode.updatePosition(position);

        CastFolderPositionProjection castFolderPositionProjection = CastFolderPositionProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(castFolderPositionProjection);
    }

    @Transactional
    public void updateCastFolderNodeInfo(CastFolderNode castFolderNode, String name, String description) {
        castFolderNode.updateTitle(name);
        castFolderNode.updateDescription(description);

        CastFolderInfoProjection castFolderInfoProjection = CastFolderInfoProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(castFolderInfoProjection);
    }

}
