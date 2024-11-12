package com.owing.node.folder.cast.service;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.owing.common.annotation.DomainService;
import com.owing.core.dnd.base.adapter.BaseDndAdapter;
import com.owing.core.dnd.base.orderStrategy.OrderingStrategy;
import com.owing.core.dnd.base.repository.BaseDndRepository;
import com.owing.core.dnd.folder.service.BaseFolderDomainService;
import com.owing.node.folder.cast.adapter.CastFolderNodeAdapter;
import com.owing.node.folder.cast.model.CastFolderNode;
import com.owing.node.folder.cast.model.projection.CastFolderDeleteProjection;
import com.owing.node.folder.cast.model.projection.CastFolderInfoProjection;
import com.owing.node.folder.cast.model.projection.CastFolderTitleProjection;
import com.owing.node.folder.cast.repository.CastFolderNodeRepository;

import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CastFolderNodeDomainService extends BaseFolderDomainService<CastFolderNode> {

    private final CastFolderNodeRepository castFolderNodeRepository;
    private final Neo4jTemplate neo4jTemplate;
    private final CastFolderNodeAdapter castFolderNodeAdapter;
    private final CastFolderShiftOrderingStrategy castFolderShiftOrderingStrategy;

    @Override
    public CastFolderNode getEntity(Long folderId) {
        return castFolderNodeAdapter.findOneById(folderId);
    }

    @Override
    @Transactional
    public void deleteEntity(CastFolderNode entity) {
        orderingStrategy().reorderEntity(entity);
        entity.delete();
        CastFolderDeleteProjection deleteProjection = CastFolderDeleteProjection.from(entity);
        neo4jTemplate.save(CastFolderNode.class).one(deleteProjection);
    }

    @Override
    @Transactional
    public CastFolderNode updateName(CastFolderNode entity, CastFolderNode newEntity) {
        entity.updateTitle(newEntity.getName());
        CastFolderTitleProjection titleProjection = CastFolderTitleProjection.from(entity);
        neo4jTemplate.save(CastFolderNode.class).one(titleProjection);
        return entity;
    }

    @Transactional
    public void updateCastFolderNodeInfo(CastFolderNode castFolderNode, String name, String description) {
        castFolderNode.updateTitle(name);
        castFolderNode.updateDescription(description);

        CastFolderInfoProjection castFolderInfoProjection = CastFolderInfoProjection.from(castFolderNode);
        neo4jTemplate.save(CastFolderNode.class).one(castFolderInfoProjection);
    }

    // Bean Setting
    @Override
    protected BaseDndRepository<CastFolderNode> dndRepository() {
        return this.castFolderNodeRepository;
    }

    @Override
    protected BaseDndAdapter<CastFolderNode> dndEntityAdapter() {
        return this.castFolderNodeAdapter;
    }

    @Override
    protected OrderingStrategy<CastFolderNode> orderingStrategy() {
        return this.castFolderShiftOrderingStrategy;
    }

}
