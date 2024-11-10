package com.owing.node.folder.cast.service;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.model.BaseDnd;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.service.BaseFolderDomainService;
import com.owing.node.domains.project.model.ProjectNode;
import com.owing.node.folder.cast.adaptor.CastFolderNodeAdaptor;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderDeleteProjection;
import com.owing.node.folder.cast.model.projection.CastFolderInfoProjection;
import com.owing.node.folder.cast.model.projection.CastFolderPositionProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastFolderNodeDomainService extends BaseFolderDomainService<CastFolderNode> {

    private final CastFolderNodeRepository castFolderNodeRepository;
    private final Neo4jTemplate neo4jTemplate;
    private final CastFolderNodeAdaptor castFolderNodeAdaptor;
    private final CastFolderShiftOrderingStrategy castFolderShiftOrderingStrategy;

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

    @Override
    public CastFolderNode updateTitle(CastFolderNode entity, CastFolderNode newEntity) {
        return super.updateTitle(entity, newEntity);
    }

    @Override
    public CastFolderNode getEntity(Long id) {
        return super.getEntity(id);
    }

    @Override
    public CastFolderNode getOptionalEntity(Long id) {
        return super.getOptionalEntity(id);
    }

    @Override
    public List<CastFolderNode> getEntityList(Long parentId) {
        return super.getEntityList(parentId);
    }

    @Override
    public void deleteEntity(CastFolderNode entity) {
        super.deleteEntity(entity);
    }

    @Override
    public CastFolderNode updateEntityPosition(CastFolderNode entity, CastFolderNode beforeEntity, CastFolderNode afterEntity) {
        return super.updateEntityPosition(entity, beforeEntity, afterEntity);
    }

    @Override
    public CastFolderNode updateEntityPosition(CastFolderNode entity, CastFolderNode beforeEntity, CastFolderNode afterEntity, BaseDnd newFolder) {
        return super.updateEntityPosition(entity, beforeEntity, afterEntity, newFolder);
    }

    @Override
    protected BaseDndRepository<CastFolderNode> dndRepository() {
        return this.castFolderNodeRepository;
    }

    @Override
    protected BaseDndAdapter<CastFolderNode> dndEntityAdapter() {
        return this.castFolderNodeAdaptor;
    }

    @Override
    protected OrderingStrategy<CastFolderNode> orderingStrategy() {
        return this.castFolderShiftOrderingStrategy;
    }
}
